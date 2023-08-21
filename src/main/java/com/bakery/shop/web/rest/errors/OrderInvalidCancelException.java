package com.bakery.shop.web.rest.errors;

public class OrderInvalidCancelException extends ErrorEntityValidationException {

    private static final long serialVersionUID = 2965783886398036786L;

    public OrderInvalidCancelException() {
        super("Current order status is not allowed to cancel", "order", "invalidCancel");
    }
}
