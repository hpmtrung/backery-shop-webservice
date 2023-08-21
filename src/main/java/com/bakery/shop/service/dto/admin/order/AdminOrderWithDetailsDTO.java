package com.bakery.shop.service.dto.admin.order;

import com.bakery.shop.service.dto.OrderReceiverDTO;
import java.time.Instant;
import java.util.List;

public class AdminOrderWithDetailsDTO {

    private Long id;
    private Instant createdAt;
    private Integer statusId;
    private long total;
    private String customerName;
    private boolean canCancel;
    private boolean paidByCash;
    private OrderReceiverDTO receiverInfo;
    private List<AdminOrderDetailDTO> details;

    public AdminOrderWithDetailsDTO() {}

    public Long getId() {
        return id;
    }

    public AdminOrderWithDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public OrderReceiverDTO getReceiverInfo() {
        return receiverInfo;
    }

    public AdminOrderWithDetailsDTO setReceiverInfo(OrderReceiverDTO receiverInfo) {
        this.receiverInfo = receiverInfo;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public AdminOrderWithDetailsDTO setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public AdminOrderWithDetailsDTO setStatusId(Integer statusId) {
        this.statusId = statusId;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public AdminOrderWithDetailsDTO setTotal(long total) {
        this.total = total;
        return this;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public AdminOrderWithDetailsDTO setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
        return this;
    }

    public List<AdminOrderDetailDTO> getDetails() {
        return details;
    }

    public AdminOrderWithDetailsDTO setDetails(List<AdminOrderDetailDTO> details) {
        this.details = details;
        return this;
    }

    public boolean isPaidByCash() {
        return paidByCash;
    }

    public AdminOrderWithDetailsDTO setPaidByCash(boolean paidByCash) {
        this.paidByCash = paidByCash;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public AdminOrderWithDetailsDTO setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }
}
