package com.adriangraczyk.employeeservice.web.rest;

import com.adriangraczyk.employeeservice.domain.enumeration.Role;
import com.adriangraczyk.employeeservice.service.EmployeeService;
import com.adriangraczyk.employeeservice.service.dto.EmployeeHierarchyDTO;
import com.adriangraczyk.employeeservice.web.rest.errors.BadRequestAlertException;
import com.adriangraczyk.employeeservice.service.dto.EmployeeDTO;

import com.adriangraczyk.employeeservice.web.rest.errors.BusinessException;
import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

/**
 * REST controller for managing {@link com.adriangraczyk.employeeservice.domain.Employee}.
 */
@RestController
@RequestMapping("/api")
public class EmployeeResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeResource.class);

    private static final String ENTITY_NAME = "employeeserviceEmployee";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EmployeeService employeeService;

    public EmployeeResource(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * {@code POST  /employees} : Create a new employee.
     *
     * @param employeeDTO the employeeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new employeeDTO, or with status {@code 400 (Bad Request)} if the employee has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/employees")
    public ResponseEntity<EmployeeDTO> createEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        log.debug("REST request to save Employee : {}", employeeDTO);
        if (employeeDTO.getId() != null) {
            throw new BadRequestAlertException("A new employee cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if(employeeDTO.getPesel() != null && employeeService.existsByPesel(employeeDTO.getPesel())){
            throw new BusinessException("A new employee cannot have PESEL that already exists", ENTITY_NAME, "idexists");
        }
        if(Role.EMPLOYEE.equals(employeeDTO.getRole()) &&
            employeeDTO.getManagerId() != null &&
            employeeService.countByManagerId(employeeDTO.getManagerId()) >= 5){
            throw new BusinessException("A new employee cannot be assigned to given manager, manager already has 5 employees", ENTITY_NAME, "peselexists");
        }
        if(Role.DIRECTOR.equals(employeeDTO.getRole()) && employeeService.countByRole(Role.DIRECTOR) >= 5) {
            throw new BusinessException("Cannot add more than 5 employee with role DIRECTOR", ENTITY_NAME, "toomanydirector");
        }
        EmployeeDTO result = employeeService.save(employeeDTO);
        return ResponseEntity.created(new URI("/api/employees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /employees} : Updates an existing employee.
     *
     * @param employeeDTO the employeeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated employeeDTO,
     * or with status {@code 400 (Bad Request)} if the employeeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the employeeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/employees")
    public ResponseEntity<EmployeeDTO> updateEmployee(@Valid @RequestBody EmployeeDTO employeeDTO) throws URISyntaxException {
        log.debug("REST request to update Employee : {}", employeeDTO);
        if (employeeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EmployeeDTO result = employeeService.save(employeeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, employeeDTO.getId()))
            .body(result);
    }

    /**
     * {@code GET  /employees} : get all the employees.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of employees in body.
     */
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(Pageable pageable) {
        log.debug("REST request to get a page of Employees");
        Page<EmployeeDTO> page = employeeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /employees/:id} : get the "id" employee.
     *
     * @param id the id of the employeeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the employeeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/employees/{id}")
    public ResponseEntity<EmployeeDTO> getEmployee(@PathVariable String id) {
        log.debug("REST request to get Employee : {}", id);
        Optional<EmployeeDTO> employeeDTO = employeeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(employeeDTO);
    }

    /**
     * {@code DELETE  /employees/:id} : delete the "id" employee.
     *
     * @param id the id of the employeeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable String id) {
        log.debug("REST request to delete Employee : {}", id);
        employeeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }

    @GetMapping("/employees/{id}/hierarchy")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity<EmployeeHierarchyDTO> getHierarchyForEmployee(@PathVariable String id) {
        log.debug("REST request to hierarchy Employee : {}", id);
        EmployeeHierarchyDTO hierarchyDTO = employeeService.findEmployeeHierarchy(id);
        return ResponseUtil.wrapOrNotFound(ofNullable(hierarchyDTO));
    }
}
