package com.bakery.shop.web.rest.errors;

public class DeleteSoldProductVariantException extends ErrorEntityValidationException {

    private static final long serialVersionUID = 1L;

    public DeleteSoldProductVariantException() {
        super("Sold product variant can't be deleted", "product", "soldVariant");
    }
}
