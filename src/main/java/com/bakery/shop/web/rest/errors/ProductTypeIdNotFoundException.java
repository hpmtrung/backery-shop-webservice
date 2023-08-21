package com.bakery.shop.web.rest.errors;

public class ProductTypeIdNotFoundException extends ErrorEntityValidationException {

    private static final long serialVersionUID = 5697561665397665295L;

    public ProductTypeIdNotFoundException() {
        super("Product type id is not found", "product", "notFoundTypeId");
    }
}
