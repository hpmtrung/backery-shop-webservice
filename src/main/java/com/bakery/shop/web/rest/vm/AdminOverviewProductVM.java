package com.bakery.shop.web.rest.vm;

public class AdminOverviewProductVM {

    private Integer id;
    private String name;
    private String categoryName;
    private String ingredients;

    public AdminOverviewProductVM() {}

    public Integer getId() {
        return id;
    }

    public AdminOverviewProductVM setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AdminOverviewProductVM setName(String name) {
        this.name = name;
        return this;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public AdminOverviewProductVM setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public String getIngredients() {
        return ingredients;
    }

    public AdminOverviewProductVM setIngredients(String ingredients) {
        this.ingredients = ingredients;
        return this;
    }
}
