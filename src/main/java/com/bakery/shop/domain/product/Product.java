package com.bakery.shop.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.NaturalId;

@Entity(name = "Product")
@Table(name = "Product")
public class Product implements Serializable {

    private static final long serialVersionUID = -7177313517346967364L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @NaturalId(mutable = true)
    @Column(name = "name", unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryId")
    private ProductCategory category;

    @NotBlank
    @Column(name = "ingredients")
    private String ingredients;

    @Column(name = "allergens")
    private String allergens;

    @Column(name = "available")
    private boolean available;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductVariant> variants = new HashSet<>();

    public Product() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getAllergens() {
        return allergens;
    }

    public void setAllergens(String allergens) {
        this.allergens = allergens;
    }

    public List<ProductImage> getImages() {
        return images;
    }

    public void setImages(List<ProductImage> productImages) {
        this.images = productImages;
    }

    public Set<ProductVariant> getVariants() {
        return variants;
    }

    public void setVariants(Set<ProductVariant> productVariants) {
        this.variants = productVariants;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void addProductImage(ProductImage productImage) {
        images.add(productImage);
        productImage.setProduct(this);
    }

    public void removeProductImage(ProductImage productImage) {
        images.remove(productImage);
        productImage.setProduct(null);
    }

    public void addProductVariant(ProductVariant productVariant) {
        variants.add(productVariant);
        productVariant.setProduct(this);
    }

    public void removeProductVariant(ProductVariant productVariant) {
        variants.remove(productVariant);
        productVariant.setProduct(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return name.equals(product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // prettier-ignore
  @Override
  public String toString() {
    return "Product{"
        + "id="
        + id
        + ", name='"
        + name
        + '\''
        + ", category="
        + category.getName()
        + ", ingredients='"
        + ingredients
        + '\''
        + ", allergens='"
        + allergens
        + '\''
        + ", available="
        + available
        + '}';
  }
}
