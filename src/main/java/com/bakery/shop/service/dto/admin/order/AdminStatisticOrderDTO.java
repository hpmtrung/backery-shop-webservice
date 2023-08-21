package com.bakery.shop.service.dto.admin.order;

import java.time.Instant;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/** A DTO representing a statical order for Admin Homepage */
public class AdminStatisticOrderDTO {

    @NotNull
    private Long orderId;

    @NotBlank
    private String userImageUrl;

    @NotBlank
    private String userFullName;

    @NotNull
    private Instant createdDate;

    @NotNull
    private Integer statusId;

    @NotBlank
    private String statusName;

    private long orderTotal;
    private boolean paidByCash;
    private String note;

    public AdminStatisticOrderDTO() {}

    public AdminStatisticOrderDTO(
        Long orderId,
        String userImageUrl,
        String userFullName,
        Instant createdDate,
        long orderTotal,
        boolean paidByCash,
        Integer statusId,
        String statusName,
        String note
    ) {
        this.orderId = orderId;
        this.userImageUrl = userImageUrl;
        this.userFullName = userFullName;
        this.createdDate = createdDate;
        this.orderTotal = orderTotal;
        this.paidByCash = paidByCash;
        this.statusId = statusId;
        this.statusName = statusName;
        this.note = note;
    }

    public String getUserImageUrl() {
        return userImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        this.userImageUrl = userImageUrl;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public long getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(long orderTotal) {
        this.orderTotal = orderTotal;
    }

    public boolean isPaidByCash() {
        return paidByCash;
    }

    public void setPaidByCash(boolean paidByCash) {
        this.paidByCash = paidByCash;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    // prettier-ignore
  @Override
  public String toString() {
    return "AdminStatisticOrderDTO{"
        + "orderId="
        + orderId
        + ", userImageUrl='"
        + userImageUrl
        + '\''
        + ", userFullName='"
        + userFullName
        + '\''
        + ", createdDate="
        + createdDate
        + ", orderTotal="
        + orderTotal
        + ", paidByCash="
        + paidByCash
        + ", note='"
        + note
        + '\''
        + ", statusId="
        + statusId
        + ", status='"
        + statusName
        + '\''
        + '}';
  }
}
