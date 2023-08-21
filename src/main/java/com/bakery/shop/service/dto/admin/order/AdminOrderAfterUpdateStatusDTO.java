package com.bakery.shop.service.dto.admin.order;

public class AdminOrderAfterUpdateStatusDTO {

    private Long orderId;
    private Integer updatedStatusId;
    private String updatedStatusName;
    private boolean canCancel;

    public AdminOrderAfterUpdateStatusDTO() {}

    public Long getOrderId() {
        return orderId;
    }

    public AdminOrderAfterUpdateStatusDTO setOrderId(Long orderId) {
        this.orderId = orderId;
        return this;
    }

    public Integer getUpdatedStatusId() {
        return updatedStatusId;
    }

    public AdminOrderAfterUpdateStatusDTO setUpdatedStatusId(Integer updatedStatusId) {
        this.updatedStatusId = updatedStatusId;
        return this;
    }

    public String getUpdatedStatusName() {
        return updatedStatusName;
    }

    public AdminOrderAfterUpdateStatusDTO setUpdatedStatusName(String updatedStatusName) {
        this.updatedStatusName = updatedStatusName;
        return this;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public AdminOrderAfterUpdateStatusDTO setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
        return this;
    }
}
