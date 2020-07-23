package com.adriangraczyk.errorservice.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A Error.
 */
@Document(collection = "error")
public class Error implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Size(max = 500)
    @Field("exception_name")
    private String exceptionName;

    @Size(max = 500)
    @Field("message")
    private String message;

    @Size(max = 5000)
    @Field("stack_trace")
    private String stackTrace;

    @Field("created_at")
    private Instant createdAt;

    @Size(max = 200)
    @Field("service_name")
    private String serviceName;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public Error exceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
        return this;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getMessage() {
        return message;
    }

    public Error message(String message) {
        this.message = message;
        return this;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public Error stackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
        return this;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Error createdAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getServiceName() {
        return serviceName;
    }

    public Error serviceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Error)) {
            return false;
        }
        return id != null && id.equals(((Error) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Error{" +
            "id=" + getId() +
            ", exceptionName='" + getExceptionName() + "'" +
            ", message='" + getMessage() + "'" +
            ", stackTrace='" + getStackTrace() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            "}";
    }
}
