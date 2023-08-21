package com.bakery.shop.web.rest.errors;

public class OrderAccountNotFoundException extends ErrorEntityValidationException {

    private static final long serialVersionUID = -1687445542572843628L;

    public OrderAccountNotFoundException() {
        super("Order id is incorrect", "order", "notExistedFromAccount");
    }
}
