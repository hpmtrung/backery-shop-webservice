package com.bakery.shop.web.rest.vm;

import com.bakery.shop.domain.product.ProductCategory;

public class PublicCategoryVM {

    private Integer id;
    private String name;
    private String imageUrl;
    private String icon;
    private String banner;

    public PublicCategoryVM() {}

    public PublicCategoryVM(ProductCategory category) {
        this.id = category.getId();
        this.name = category.getName();
        this.imageUrl = category.getImageUrl();
        this.icon = category.getIcon();
        this.banner = category.getBanner();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getIcon() {
        return icon;
    }

    public String getBanner() {
        return banner;
    }

    public PublicCategoryVM setId(Integer id) {
        this.id = id;
        return this;
    }

    public PublicCategoryVM setName(String name) {
        this.name = name;
        return this;
    }

    public PublicCategoryVM setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public PublicCategoryVM setIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public PublicCategoryVM setBanner(String banner) {
        this.banner = banner;
        return this;
    }
}
