package com.bakery.shop.service.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserCheckOutInitDTO {

    @NotBlank
    private String address;

    @NotBlank
    @Pattern(regexp = "[\\d]{10}")
    private String phone;

    private boolean paidByCash;
    private String note;

    public UserCheckOutInitDTO() {}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isPaidByCash() {
        return paidByCash;
    }

    public void setPaidByCash(boolean paidByCash) {
        this.paidByCash = paidByCash;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
