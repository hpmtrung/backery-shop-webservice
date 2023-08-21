package com.bakery.shop.web.rest.errors;

public class AccountIdNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = -1062523375921826100L;

    public AccountIdNotFoundException() {
        super("Account id is not found", "account", "notFoundId");
    }
}
