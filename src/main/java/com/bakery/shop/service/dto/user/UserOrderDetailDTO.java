package com.bakery.shop.service.dto.user;

public class UserOrderDetailDTO {

    private String productName;
    private String typeName;
    private int quantity;
    private long unitPrice;
    private String imageUrl;

    public UserOrderDetailDTO() {}

    public String getProductName() {
        return productName;
    }

    public UserOrderDetailDTO setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserOrderDetailDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public UserOrderDetailDTO setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public UserOrderDetailDTO setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public UserOrderDetailDTO setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }
}
