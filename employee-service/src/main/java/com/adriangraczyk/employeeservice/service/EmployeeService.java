package com.adriangraczyk.employeeservice.service;

import com.adriangraczyk.employeeservice.domain.Employee;
import com.adriangraczyk.employeeservice.domain.enumeration.Role;
import com.adriangraczyk.employeeservice.repository.EmployeeRepository;
import com.adriangraczyk.employeeservice.service.dto.EmployeeDTO;
import com.adriangraczyk.employeeservice.service.dto.EmployeeHierarchyDTO;
import com.adriangraczyk.employeeservice.service.mapper.EmployeeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Employee}.
 */
@Service
public class EmployeeService {

    private final Logger log = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
    }

    /**
     * Save a employee.
     *
     * @param employeeDTO the entity to save.
     * @return the persisted entity.
     */
    public EmployeeDTO save(EmployeeDTO employeeDTO) {
        log.debug("Request to save Employee : {}", employeeDTO);
        Employee employee = employeeMapper.toEntity(employeeDTO);
        employee = employeeRepository.save(employee);
        return employeeMapper.toDto(employee);
    }

    /**
     * Get all the employees.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<EmployeeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable)
            .map(employeeMapper::toDto);
    }


    /**
     * Get one employee by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<EmployeeDTO> findOne(String id) {
        log.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id)
            .map(employeeMapper::toDto);
    }

    /**
     * Delete the employee by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }

    public EmployeeHierarchyDTO findEmployeeHierarchy(String id) {
        Optional<Employee> employee = employeeRepository.findById(id);
        EmployeeHierarchyDTO hierarchy = new EmployeeHierarchyDTO();
        employee.ifPresent(e -> hierarchy.setWorkers(employeeRepository.findAllByManagerId(e.getId())
            .stream()
            .map(employeeMapper::toDto)
            .collect(Collectors.toList())));
        employee.map(Employee::getManager)
            .map(Employee::getId)
            .flatMap(employeeRepository::findById)
            .map(employeeMapper::toDto)
            .ifPresent(hierarchy::setManager);
        return hierarchy;
    }

    public boolean existsByPesel(String pesel) {
        return employeeRepository.existsByPesel(pesel);
    }

    public Long countByManagerId(String managerId) {
        return employeeRepository.countByManagerId(managerId);
    }

    public Long countByRole(Role role){
        return employeeRepository.countByRole(role);
    }
}
