package com.bakery.shop.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class EntityNotFoundException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 2100185299488471691L;

    private final String entityName;

    private final String errorKey;

    public EntityNotFoundException(String defaultMessage, String entityName, String errorKey) {
        super(ErrorConstants.ENTITY_TYPE, defaultMessage, Status.NOT_FOUND);
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
