package com.bakery.shop.service.dto.user;

import java.time.Instant;

public class UserOverviewOrderDTO {

    private Long id;
    private Instant createdAt;
    private Integer statusId;
    private long total;
    private UserOrderDetailDTO firstDetail;
    private int numRemainingDetails;

    public UserOverviewOrderDTO() {}

    public Long getId() {
        return id;
    }

    public UserOverviewOrderDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UserOverviewOrderDTO setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public UserOverviewOrderDTO setStatusId(Integer statusId) {
        this.statusId = statusId;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public UserOverviewOrderDTO setTotal(long total) {
        this.total = total;
        return this;
    }

    public UserOrderDetailDTO getFirstDetail() {
        return firstDetail;
    }

    public UserOverviewOrderDTO setFirstDetail(UserOrderDetailDTO firstDetail) {
        this.firstDetail = firstDetail;
        return this;
    }

    public int getNumRemainingDetails() {
        return numRemainingDetails;
    }

    public UserOverviewOrderDTO setNumRemainingDetails(int numRemainingDetails) {
        this.numRemainingDetails = numRemainingDetails;
        return this;
    }
}
