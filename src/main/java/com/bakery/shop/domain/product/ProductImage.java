package com.bakery.shop.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "ProductImage")
@Table(name = "ProductImage")
public class ProductImage implements Serializable {

    private static final long serialVersionUID = 8707888355645742882L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    private String imagePath;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    public ProductImage() {}

    public Integer getId() {
        return id;
    }

    public ProductImage setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ProductImage setImagePath(String image) {
        this.imagePath = image;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public ProductImage setProduct(Product product) {
        this.product = product;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage that = (ProductImage) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
  @Override
  public String toString() {
    return "ProductImage{" + "id=" + id + ", image='" + imagePath + '\'' + '}';
  }
}
