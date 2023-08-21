package com.bakery.shop.service.dto.admin.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AdminProductDTO {

    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Integer categoryId;

    @NotBlank
    private String ingredients;

    private String allergens;
    private boolean available;

    public AdminProductDTO() {}

    public Integer getId() {
        return id;
    }

    public AdminProductDTO setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AdminProductDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getIngredients() {
        return ingredients;
    }

    public AdminProductDTO setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public AdminProductDTO setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public String getAllergens() {
        return allergens;
    }

    public AdminProductDTO setAllergens(String allergens) {
        this.allergens = allergens;
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public AdminProductDTO setAvailable(boolean available) {
        this.available = available;
        return this;
    }
}
