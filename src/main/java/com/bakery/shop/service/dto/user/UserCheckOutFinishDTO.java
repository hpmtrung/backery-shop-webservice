package com.bakery.shop.service.dto.user;

public class UserCheckOutFinishDTO {

    private Long orderId;

    public UserCheckOutFinishDTO() {}

    public UserCheckOutFinishDTO(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
