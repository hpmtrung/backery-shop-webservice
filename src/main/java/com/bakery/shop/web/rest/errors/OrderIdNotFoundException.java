package com.bakery.shop.web.rest.errors;

public class OrderIdNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public OrderIdNotFoundException() {
        super("Order id is not found", "order", "notFoundId");
    }
}
