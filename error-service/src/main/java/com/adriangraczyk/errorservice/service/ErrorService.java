package com.adriangraczyk.errorservice.service;

import com.adriangraczyk.errorservice.domain.Error;
import com.adriangraczyk.errorservice.repository.ErrorRepository;
import com.adriangraczyk.errorservice.service.dto.ErrorDTO;
import com.adriangraczyk.errorservice.service.mapper.ErrorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;


/**
 * Service Implementation for managing {@link Error}.
 */
@Service
public class ErrorService {

    private final Logger log = LoggerFactory.getLogger(ErrorService.class);

    private final ErrorRepository errorRepository;

    private final ErrorMapper errorMapper;

    public ErrorService(ErrorRepository errorRepository, ErrorMapper errorMapper) {
        this.errorRepository = errorRepository;
        this.errorMapper = errorMapper;
    }

    /**
     * Save a error.
     *
     * @param errorDTO the entity to save.
     * @return the persisted entity.
     */
    public Mono<ErrorDTO> save(ErrorDTO errorDTO) {
        errorDTO.setCreatedAt(Instant.now());
        log.debug("Request to save Error : {}", errorDTO);
        return errorRepository.save(errorMapper.toEntity(errorDTO))
            .map(errorMapper::toDto)
;    }

    /**
     * Get all the errors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Flux<ErrorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Errors");
        return errorRepository.findAllBy(pageable)
            .map(errorMapper::toDto);
    }


    /**
     * Returns the number of errors available.
     *
     */
    public Mono<Long> countAll() {
        return errorRepository.count();
    }

    /**
     * Get one error by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Mono<ErrorDTO> findOne(String id) {
        log.debug("Request to get Error : {}", id);
        return errorRepository.findById(id)
            .map(errorMapper::toDto);
    }

    /**
     * Delete the error by id.
     *
     * @param id the id of the entity.
     */
    public Mono<Void> delete(String id) {
        log.debug("Request to delete Error : {}", id);
        return errorRepository.deleteById(id);    }
}
