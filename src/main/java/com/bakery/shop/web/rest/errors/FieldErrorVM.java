package com.bakery.shop.web.rest.errors;

import java.io.Serializable;

public class FieldErrorVM implements Serializable {

    private static final long serialVersionUID = 1L;

    // Base name of DTO or VM class
    // private final String objectName;

    private final String field;

    private final String message;

    public FieldErrorVM(String field, String message) {
        // this.objectName = dto;
        this.field = field;
        this.message = message;
    }

    // public String getObjectName() {
    //     return objectName;
    // }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
