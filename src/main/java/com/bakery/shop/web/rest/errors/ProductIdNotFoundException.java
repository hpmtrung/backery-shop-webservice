package com.bakery.shop.web.rest.errors;

public class ProductIdNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public ProductIdNotFoundException() {
        super("Product id is not found", "product", "notFoundId");
    }
}
