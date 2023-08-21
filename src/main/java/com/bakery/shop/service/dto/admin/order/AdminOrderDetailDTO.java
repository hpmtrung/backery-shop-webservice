package com.bakery.shop.service.dto.admin.order;

public class AdminOrderDetailDTO {

    private String productName;
    private String typeName;
    private int quantity;
    private long unitPrice;

    public AdminOrderDetailDTO() {}

    public String getProductName() {
        return productName;
    }

    public AdminOrderDetailDTO setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public AdminOrderDetailDTO setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public AdminOrderDetailDTO setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public AdminOrderDetailDTO setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }
}
