package com.adriangraczyk.errorservice.web.rest;

import com.adriangraczyk.errorservice.ErrorserviceApp;
import com.adriangraczyk.errorservice.domain.Error;
import com.adriangraczyk.errorservice.repository.ErrorRepository;
import com.adriangraczyk.errorservice.service.ErrorService;
import com.adriangraczyk.errorservice.service.dto.ErrorDTO;
import com.adriangraczyk.errorservice.service.mapper.ErrorMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;

/**
 * Integration tests for the {@link ErrorResource} REST controller.
 */
@SpringBootTest(classes = ErrorserviceApp.class)
@AutoConfigureWebTestClient
@WithMockUser
public class ErrorResourceIT {

    private static final String DEFAULT_EXCEPTION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EXCEPTION_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_STACK_TRACE = "AAAAAAAAAA";
    private static final String UPDATED_STACK_TRACE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_SERVICE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_NAME = "BBBBBBBBBB";

    @Autowired
    private ErrorRepository errorRepository;

    @Autowired
    private ErrorMapper errorMapper;

    @Autowired
    private ErrorService errorService;

    @Autowired
    private WebTestClient webTestClient;

    private Error error;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Error createEntity() {
        Error error = new Error()
            .exceptionName(DEFAULT_EXCEPTION_NAME)
            .message(DEFAULT_MESSAGE)
            .stackTrace(DEFAULT_STACK_TRACE)
            .createdAt(DEFAULT_CREATED_AT)
            .serviceName(DEFAULT_SERVICE_NAME);
        return error;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Error createUpdatedEntity() {
        Error error = new Error()
            .exceptionName(UPDATED_EXCEPTION_NAME)
            .message(UPDATED_MESSAGE)
            .stackTrace(UPDATED_STACK_TRACE)
            .createdAt(UPDATED_CREATED_AT)
            .serviceName(UPDATED_SERVICE_NAME);
        return error;
    }

    @BeforeEach
    public void initTest() {
        errorRepository.deleteAll().block();
        error = createEntity();
    }

    @Test
    public void createError() throws Exception {
        int databaseSizeBeforeCreate = errorRepository.findAll().collectList().block().size();
        // Create the Error
        ErrorDTO errorDTO = errorMapper.toDto(error);
        webTestClient.post().uri("/api/errors")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(errorDTO))
            .exchange()
            .expectStatus().isCreated();

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll().collectList().block();
        assertThat(errorList).hasSize(databaseSizeBeforeCreate + 1);
        Error testError = errorList.get(errorList.size() - 1);
        assertThat(testError.getExceptionName()).isEqualTo(DEFAULT_EXCEPTION_NAME);
        assertThat(testError.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testError.getStackTrace()).isEqualTo(DEFAULT_STACK_TRACE);
        assertThat(testError.getServiceName()).isEqualTo(DEFAULT_SERVICE_NAME);
    }

    @Test
    public void createErrorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = errorRepository.findAll().collectList().block().size();

        // Create the Error with an existing ID
        error.setId("existing_id");
        ErrorDTO errorDTO = errorMapper.toDto(error);

        // An entity with an existing ID cannot be created, so this API call must fail
        webTestClient.post().uri("/api/errors")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(errorDTO))
            .exchange()
            .expectStatus().isBadRequest();

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll().collectList().block();
        assertThat(errorList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllErrors() {
        // Initialize the database
        errorRepository.save(error).block();

        // Get all the errorList
        webTestClient.get().uri("/api/errors?sort=id,desc")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.[*].id").value(hasItem(error.getId()))
            .jsonPath("$.[*].exceptionName").value(hasItem(DEFAULT_EXCEPTION_NAME))
            .jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE))
            .jsonPath("$.[*].stackTrace").value(hasItem(DEFAULT_STACK_TRACE))
            .jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString()))
            .jsonPath("$.[*].serviceName").value(hasItem(DEFAULT_SERVICE_NAME));
    }

    @Test
    public void getError() {
        // Initialize the database
        errorRepository.save(error).block();

        // Get the error
        webTestClient.get().uri("/api/errors/{id}", error.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.id").value(is(error.getId()))
            .jsonPath("$.exceptionName").value(is(DEFAULT_EXCEPTION_NAME))
            .jsonPath("$.message").value(is(DEFAULT_MESSAGE))
            .jsonPath("$.stackTrace").value(is(DEFAULT_STACK_TRACE))
            .jsonPath("$.createdAt").value(is(DEFAULT_CREATED_AT.toString()))
            .jsonPath("$.serviceName").value(is(DEFAULT_SERVICE_NAME));
    }
    @Test
    public void getNonExistingError() {
        // Get the error
        webTestClient.get().uri("/api/errors/{id}", Long.MAX_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound();
    }

    @Test
    public void updateError() throws Exception {
        // Initialize the database
        errorRepository.save(error).block();

        int databaseSizeBeforeUpdate = errorRepository.findAll().collectList().block().size();

        // Update the error
        Error updatedError = errorRepository.findById(error.getId()).block();
        updatedError
            .exceptionName(UPDATED_EXCEPTION_NAME)
            .message(UPDATED_MESSAGE)
            .stackTrace(UPDATED_STACK_TRACE)
            .createdAt(UPDATED_CREATED_AT)
            .serviceName(UPDATED_SERVICE_NAME);
        ErrorDTO errorDTO = errorMapper.toDto(updatedError);

        webTestClient.put().uri("/api/errors")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(errorDTO))
            .exchange()
            .expectStatus().isOk();

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll().collectList().block();
        assertThat(errorList).hasSize(databaseSizeBeforeUpdate);
        Error testError = errorList.get(errorList.size() - 1);
        assertThat(testError.getExceptionName()).isEqualTo(UPDATED_EXCEPTION_NAME);
        assertThat(testError.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testError.getStackTrace()).isEqualTo(UPDATED_STACK_TRACE);
        assertThat(testError.getServiceName()).isEqualTo(UPDATED_SERVICE_NAME);
    }

    @Test
    public void updateNonExistingError() throws Exception {
        int databaseSizeBeforeUpdate = errorRepository.findAll().collectList().block().size();

        // Create the Error
        ErrorDTO errorDTO = errorMapper.toDto(error);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        webTestClient.put().uri("/api/errors")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(TestUtil.convertObjectToJsonBytes(errorDTO))
            .exchange()
            .expectStatus().isBadRequest();

        // Validate the Error in the database
        List<Error> errorList = errorRepository.findAll().collectList().block();
        assertThat(errorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deleteError() {
        // Initialize the database
        errorRepository.save(error).block();

        int databaseSizeBeforeDelete = errorRepository.findAll().collectList().block().size();

        // Delete the error
        webTestClient.delete().uri("/api/errors/{id}", error.getId())
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNoContent();

        // Validate the database contains one less item
        List<Error> errorList = errorRepository.findAll().collectList().block();
        assertThat(errorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
