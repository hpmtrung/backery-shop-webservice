package com.bakery.shop.service.dto.user;

import java.time.Instant;
import java.util.List;

public class UserOrderWithDetailsDTO {

    private Long id;
    private Instant createdAt;
    private Integer statusId;
    private long total;
    private boolean canCancel;
    private boolean paidByCash;
    private ReceiverInfo receiverInfo;
    private List<UserOrderDetailDTO> details;

    public UserOrderWithDetailsDTO() {}

    public Long getId() {
        return id;
    }

    public UserOrderWithDetailsDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public boolean isPaidByCash() {
        return paidByCash;
    }

    public UserOrderWithDetailsDTO setPaidByCash(boolean paidByCash) {
        this.paidByCash = paidByCash;
        return this;
    }

    public ReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public UserOrderWithDetailsDTO setReceiverInfo(ReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public UserOrderWithDetailsDTO setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public UserOrderWithDetailsDTO setStatusId(Integer statusId) {
        this.statusId = statusId;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public UserOrderWithDetailsDTO setTotal(long total) {
        this.total = total;
        return this;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public UserOrderWithDetailsDTO setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
        return this;
    }

    public List<UserOrderDetailDTO> getDetails() {
        return details;
    }

    public UserOrderWithDetailsDTO setDetails(List<UserOrderDetailDTO> details) {
        this.details = details;
        return this;
    }

    public static class ReceiverInfo {

        private String address;
        private String phone;
        private String note;

        public ReceiverInfo() {}

        public String getAddress() {
            return address;
        }

        public ReceiverInfo setAddress(String address) {
            this.address = address;
            return this;
        }

        public String getPhone() {
            return phone;
        }

        public ReceiverInfo setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public String getNote() {
            return note;
        }

        public ReceiverInfo setNote(String note) {
            this.note = note;
            return this;
        }
    }
}
