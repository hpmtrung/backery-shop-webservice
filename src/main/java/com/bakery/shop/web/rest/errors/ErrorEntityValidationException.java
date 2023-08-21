package com.bakery.shop.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ErrorEntityValidationException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    private final String entityName;

    private final String errorKey;

    public ErrorEntityValidationException(String title, String entityName, String errorKey) {
        super(ErrorConstants.ENTITY_TYPE, title, Status.BAD_REQUEST);
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getErrorKey() {
        return errorKey;
    }
}
