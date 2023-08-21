package com.bakery.shop.web.rest.errors;

public class CategoryIdNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public CategoryIdNotFoundException() {
        super("Category id is not found", "category", "notFoundId");
    }
}
