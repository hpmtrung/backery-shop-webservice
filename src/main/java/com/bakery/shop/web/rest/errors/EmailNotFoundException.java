package com.bakery.shop.web.rest.errors;

public class EmailNotFoundException extends ErrorEntityValidationException {

    private static final long serialVersionUID = 1L;

    public EmailNotFoundException() {
        super("Email is not found", "account", "notFoundEmail");
    }
}
