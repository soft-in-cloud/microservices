package com.adriangraczyk.employeeservice.service.dto;

import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the Error entity.
 */
public class ErrorDTO implements Serializable {

    private String id;
    private String exceptionName;
    private String message;
    private String stackTrace;
    private Instant createdAt;
    private String serviceName;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof com.adriangraczyk.employeeservice.service.dto.ErrorDTO)) {
            return false;
        }

        return id != null && id.equals(((com.adriangraczyk.employeeservice.service.dto.ErrorDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ErrorDTO{" +
            "id=" + getId() +
            ", exceptionName='" + getExceptionName() + "'" +
            ", message='" + getMessage() + "'" +
            ", stackTrace='" + getStackTrace() + "'" +
            ", createdAt='" + getCreatedAt() + "'" +
            ", serviceName='" + getServiceName() + "'" +
            "}";
    }
}
