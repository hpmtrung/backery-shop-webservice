package com.bakery.shop.service.dto.admin.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AdminProductCreateDTO {

    @NotBlank
    private String name;

    @NotNull
    private Integer categoryId;

    private String ingredients;
    private String allergens;
    private boolean available;

    public AdminProductCreateDTO() {}

    public String getName() {
        return name;
    }

    public AdminProductCreateDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public AdminProductCreateDTO setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getIngredients() {
        return ingredients;
    }

    public AdminProductCreateDTO setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public String getAllergens() {
        return allergens;
    }

    public AdminProductCreateDTO setAllergens(String allergens) {
        this.allergens = allergens;
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public AdminProductCreateDTO setAvailable(boolean available) {
        this.available = available;
        return this;
    }
}
