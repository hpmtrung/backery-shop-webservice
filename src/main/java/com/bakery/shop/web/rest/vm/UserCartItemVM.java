package com.bakery.shop.web.rest.vm;

public class UserCartItemVM {

    private Integer variantId;
    private Integer productId;
    private String productName;
    private Integer categoryId;
    private String typeName;
    private long unitPrice;
    private int quantity;

    private String imageUrl;

    public UserCartItemVM() {}

    public Integer getProductId() {
        return productId;
    }

    public UserCartItemVM setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public UserCartItemVM setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public Integer getVariantId() {
        return variantId;
    }

    public UserCartItemVM setVariantId(Integer variantId) {
        this.variantId = variantId;
        return this;
    }

    public String getProductName() {
        return productName;
    }

    public UserCartItemVM setProductName(String productName) {
        this.productName = productName;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public UserCartItemVM setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public UserCartItemVM setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public int getQuantity() {
        return quantity;
    }

    public UserCartItemVM setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserCartItemVM setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }
}
