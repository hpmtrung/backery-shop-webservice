package com.bakery.shop.domain.order;

public enum OrderStatusConstants {
    PROCESSING(1),
    DISPATCH(2),
    SHIPPED(3),
    CANCEL(4);

    private final int id;

    OrderStatusConstants(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
