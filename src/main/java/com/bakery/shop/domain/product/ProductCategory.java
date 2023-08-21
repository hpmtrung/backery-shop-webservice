package com.bakery.shop.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.NaturalId;

@Entity(name = "ProductCategory")
@Table(name = "Category")
public class ProductCategory implements Serializable {

    private static final long serialVersionUID = 8510939376915409077L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @NaturalId(mutable = true)
    @Column(name = "name", unique = true)
    private String name;

    @JsonIgnore
    @Column(name = "`image`")
    private String imageUrl;

    @JsonIgnore
    @Column(name = "icon")
    private String icon;

    @JsonIgnore
    @Column(name = "banner")
    private String banner;

    @JsonIgnore
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();

    public ProductCategory() {}

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String image) {
        this.imageUrl = image;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        products.add(product);
        product.setCategory(this);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        product.setCategory(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCategory category = (ProductCategory) o;
        return name.equals(category.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductCategory{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", icon='" + icon + '\'' +
            ", banner='" + banner + '\'' +
            '}';
    }
}
