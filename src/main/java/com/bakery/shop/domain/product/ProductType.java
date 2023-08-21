package com.bakery.shop.domain.product;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.NaturalId;

@Entity(name = "ProductType")
@Table(name = "ProductType")
public class ProductType implements Serializable {

    private static final long serialVersionUID = 8582152858330564374L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @NaturalId(mutable = true)
    @Column(name = "name", unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "productType", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private Set<ProductVariant> productVariants = new HashSet<>();

    public ProductType() {}

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

    public Set<ProductVariant> getProductVariants() {
        return productVariants;
    }

    public void setProductVariants(Set<ProductVariant> productVariants) {
        this.productVariants = productVariants;
    }

    public void addProductVariant(ProductVariant productVariant) {
        productVariants.add(productVariant);
        productVariant.setProductType(this);
    }

    public void removeProductVariant(ProductVariant productVariant) {
        productVariants.remove(productVariant);
        productVariant.setProductType(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductType that = (ProductType) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // prettier-ignore
  @Override
  public String toString() {
    return "ProductType{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
