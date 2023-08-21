package com.bakery.shop.service.dto;

public class OrderReceiverDTO {

    private String address;
    private String phone;
    private String note;

    public OrderReceiverDTO() {}

    public String getAddress() {
        return address;
    }

    public OrderReceiverDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public OrderReceiverDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getNote() {
        return note;
    }

    public OrderReceiverDTO setNote(String note) {
        this.note = note;
        return this;
    }
}
