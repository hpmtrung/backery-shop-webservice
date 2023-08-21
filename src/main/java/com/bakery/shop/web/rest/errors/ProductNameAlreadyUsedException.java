package com.bakery.shop.web.rest.errors;

public class ProductNameAlreadyUsedException extends ErrorEntityValidationException {

    private static final long serialVersionUID = 1L;

    public ProductNameAlreadyUsedException() {
        super("Product name is already used", "product", "usedName");
    }
}
