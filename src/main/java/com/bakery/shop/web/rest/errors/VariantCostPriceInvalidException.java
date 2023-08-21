package com.bakery.shop.web.rest.errors;

public class VariantCostPriceInvalidException extends ErrorEntityValidationException {

    private static final long serialVersionUID = 1L;

    public VariantCostPriceInvalidException() {
        super("Product variant has cost should be less than price", "product", "invalidCostPriceVariant");
    }
}
