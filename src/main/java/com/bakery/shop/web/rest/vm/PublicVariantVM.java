package com.bakery.shop.web.rest.vm;

public class PublicVariantVM {

    private Integer id;
    private String typeName;
    private long unitPrice;

    public PublicVariantVM() {}

    public long getUnitPrice() {
        return unitPrice;
    }

    public PublicVariantVM setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public PublicVariantVM setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getTypeName() {
        return typeName;
    }

    public PublicVariantVM setTypeName(String typeName) {
        this.typeName = typeName;
        return this;
    }
}
