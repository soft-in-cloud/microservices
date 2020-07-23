package com.adriangraczyk.employeeservice.web.rest.errors;

import java.net.URI;

public class BusinessException extends BadRequestAlertException {

    public BusinessException(String defaultMessage, String entityName, String errorKey) {
        super(defaultMessage, entityName, errorKey);
    }

    public BusinessException(URI type, String defaultMessage, String entityName, String errorKey) {
        super(type, defaultMessage, entityName, errorKey);
    }
}
