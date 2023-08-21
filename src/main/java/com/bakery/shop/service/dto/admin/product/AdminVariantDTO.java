package com.bakery.shop.service.dto.admin.product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AdminVariantDTO {

    private Integer id;

    @NotNull
    private Integer productId;

    @NotNull
    private Integer typeId;

    @Min(1000)
    private long cost;

    @Min(1000)
    private long price;

    private boolean hot;
    private boolean available;

    public AdminVariantDTO() {}

    public Integer getId() {
        return id;
    }

    public AdminVariantDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getProductId() {
        return productId;
    }

    public AdminVariantDTO setProductId(Integer productId) {
        this.productId = productId;
        return this;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public AdminVariantDTO setTypeId(Integer typeId) {
        this.typeId = typeId;
        return this;
    }

    public long getCost() {
        return cost;
    }

    public AdminVariantDTO setCost(long cost) {
        this.cost = cost;
        return this;
    }

    public long getPrice() {
        return price;
    }

    public AdminVariantDTO setPrice(long price) {
        this.price = price;
        return this;
    }

    public boolean isHot() {
        return hot;
    }

    public AdminVariantDTO setHot(boolean hot) {
        this.hot = hot;
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public AdminVariantDTO setAvailable(boolean available) {
        this.available = available;
        return this;
    }
}
