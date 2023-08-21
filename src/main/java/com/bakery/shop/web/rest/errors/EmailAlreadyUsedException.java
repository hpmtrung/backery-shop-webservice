package com.bakery.shop.web.rest.errors;

public class EmailAlreadyUsedException extends ErrorEntityValidationException {

    private static final long serialVersionUID = 1L;

    public EmailAlreadyUsedException() {
        super("Email is already used", "account", "usedEmail");
    }
}
