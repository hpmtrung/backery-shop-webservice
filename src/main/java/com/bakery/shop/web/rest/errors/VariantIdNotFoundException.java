package com.bakery.shop.web.rest.errors;

public class VariantIdNotFoundException extends EntityNotFoundException {

    private static final long serialVersionUID = 1L;

    public VariantIdNotFoundException() {
        super("Variant id is not found", "product", "notFoundVariant");
    }
}
