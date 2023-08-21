package com.bakery.shop.web.rest.errors;

public class PhoneAlreadyUsedException extends ErrorEntityValidationException {

    private static final long serialVersionUID = 1L;

    public PhoneAlreadyUsedException() {
        super("Phone is already in use!", "account", "usedPhone");
    }
}
