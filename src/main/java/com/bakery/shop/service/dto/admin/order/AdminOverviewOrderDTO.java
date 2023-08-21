package com.bakery.shop.service.dto.admin.order;

import java.time.Instant;

public class AdminOverviewOrderDTO {

    private Long id;
    private Instant createdAt;
    private Integer statusId;
    private long total;
    private String customerName;

    public AdminOverviewOrderDTO() {}

    public Long getId() {
        return id;
    }

    public AdminOverviewOrderDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public AdminOverviewOrderDTO setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public AdminOverviewOrderDTO setStatusId(Integer statusId) {
        this.statusId = statusId;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public AdminOverviewOrderDTO setTotal(long total) {
        this.total = total;
        return this;
    }

    public String getCustomerName() {
        return customerName;
    }

    public AdminOverviewOrderDTO setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }
}
