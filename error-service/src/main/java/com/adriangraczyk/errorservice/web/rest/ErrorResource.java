package com.adriangraczyk.errorservice.web.rest;

import com.adriangraczyk.errorservice.service.ErrorService;
import com.adriangraczyk.errorservice.web.rest.errors.BadRequestAlertException;
import com.adriangraczyk.errorservice.service.dto.ErrorDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.reactive.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.adriangraczyk.errorservice.domain.Error}.
 */
@RestController
@RequestMapping("/api")
public class ErrorResource {

    private final Logger log = LoggerFactory.getLogger(ErrorResource.class);

    private static final String ENTITY_NAME = "errorserviceError";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ErrorService errorService;

    public ErrorResource(ErrorService errorService) {
        this.errorService = errorService;
    }

    /**
     * {@code POST  /errors} : Create a new error.
     *
     * @param errorDTO the errorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new errorDTO, or with status {@code 400 (Bad Request)} if the error has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/errors")
    public Mono<ResponseEntity<ErrorDTO>> createError(@Valid @RequestBody ErrorDTO errorDTO) throws URISyntaxException {
        log.debug("REST request to save Error : {}", errorDTO);
        if (errorDTO.getId() != null) {
            throw new BadRequestAlertException("A new error cannot already have an ID", ENTITY_NAME, "idexists");
        }
        return errorService.save(errorDTO)
            .map(result -> {
                try {
                    return ResponseEntity.created(new URI("/api/errors/" + result.getId()))
                        .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
                        .body(result);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });
    }

    /**
     * {@code PUT  /errors} : Updates an existing error.
     *
     * @param errorDTO the errorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated errorDTO,
     * or with status {@code 400 (Bad Request)} if the errorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the errorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/errors")
    public Mono<ResponseEntity<ErrorDTO>> updateError(@Valid @RequestBody ErrorDTO errorDTO) throws URISyntaxException {
        log.debug("REST request to update Error : {}", errorDTO);
        if (errorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        return errorService.save(errorDTO)
            .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
            .map(result -> ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, result.getId()))
                .body(result)
            );
    }

    /**
     * {@code GET  /errors} : get all the errors.
     *
     * @param pageable the pagination information.
     * @param request a {@link ServerHttpRequest} request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of errors in body.
     */
    @GetMapping("/errors")
    public Mono<ResponseEntity<Flux<ErrorDTO>>> getAllErrors(Pageable pageable, ServerHttpRequest request) {
        log.debug("REST request to get a page of Errors");
        return errorService.countAll()
            .map(total -> new PageImpl<>(new ArrayList<>(), pageable, total))
            .map(page -> PaginationUtil.generatePaginationHttpHeaders(UriComponentsBuilder.fromHttpRequest(request), page))
            .map(headers -> ResponseEntity.ok().headers(headers).body(errorService.findAll(pageable)));
    }

    /**
     * {@code GET  /errors/:id} : get the "id" error.
     *
     * @param id the id of the errorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the errorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/errors/{id}")
    public Mono<ResponseEntity<ErrorDTO>> getError(@PathVariable String id) {
        log.debug("REST request to get Error : {}", id);
        Mono<ErrorDTO> errorDTO = errorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(errorDTO);
    }

    /**
     * {@code DELETE  /errors/:id} : delete the "id" error.
     *
     * @param id the id of the errorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/errors/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Void>> deleteError(@PathVariable String id) {
        log.debug("REST request to delete Error : {}", id);
        return errorService.delete(id)            .map(result -> ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build()
        );
    }
}
