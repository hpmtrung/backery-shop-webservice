package com.bakery.shop.web.rest.errors;

public class VariantIdAlreadyUsedException extends ErrorEntityValidationException {

    private static final long serialVersionUID = 1L;

    public VariantIdAlreadyUsedException() {
        super("Variant is already existed", "product", "existedVariant");
    }
}
