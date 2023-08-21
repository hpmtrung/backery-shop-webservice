package com.bakery.shop.service.dto;

import java.util.List;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class PublicCheckOutInitDTO {

    @NotBlank
    private String address;

    @NotBlank
    private String phone;

    private boolean paidByCash;
    private String note;

    @NotNull
    @Size(min = 1)
    private List<CartDetailVM> cartDetails;

    public PublicCheckOutInitDTO() {}

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

    public List<CartDetailVM> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetailVM> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public static class CartDetailVM {

        @NotNull
        private Integer variantId;

        @Min(1)
        private int quantity;

        public CartDetailVM() {}

        public Integer getVariantId() {
            return variantId;
        }

        public void setVariantId(Integer variantId) {
            this.variantId = variantId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
