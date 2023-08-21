package com.bakery.shop.web.rest.vm;

import java.util.List;

public class PublicProductVM {

    private Integer id;
    private String name;
    private String ingredients;
    private String allergens;
    private List<String> imageUrls;
    private List<PublicVariantVM> variants;

    public PublicProductVM() {}

    public Integer getId() {
        return id;
    }

    public PublicProductVM setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public PublicProductVM setName(String name) {
        this.name = name;
        return this;
    }

    public String getIngredients() {
        return ingredients;
    }

    public PublicProductVM setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public String getAllergens() {
        return allergens;
    }

    public PublicProductVM setAllergens(String allergens) {
        this.allergens = allergens;
        return this;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public PublicProductVM setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
        return this;
    }

    public List<PublicVariantVM> getVariants() {
        return variants;
    }

    public PublicProductVM setVariants(List<PublicVariantVM> variants) {
        this.variants = variants;
        return this;
    }
}
