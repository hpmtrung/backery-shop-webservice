package com.bakery.shop.domain.product;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Table(name = "ProductVariant")
public class ProductVariant implements Serializable {

    private static final long serialVersionUID = 944676367135053556L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "productId")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeId")
    private ProductType productType;

    @Min(value = 1000)
    @Column(name = "cost")
    private long cost;

    @Min(value = 1000)
    @Column(name = "price")
    private long price;

    @Column(name = "hot")
    private boolean hot = false;

    @Column(name = "available")
    private boolean available = false;

    public ProductVariant() {}

    public ProductVariant(Product product, ProductType productType, long cost, long price) {
        this.product = product;
        this.productType = productType;
        this.cost = cost;
        this.price = price;
        this.hot = false;
        this.available = true;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public ProductVariant setId(Integer id) {
        this.id = id;
        return this;
    }

    public Product getProduct() {
        return product;
    }

    public ProductVariant setProduct(Product product) {
        this.product = product;
        return this;
    }

    public ProductType getProductType() {
        return productType;
    }

    public ProductVariant setProductType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public long getCost() {
        return cost;
    }

    public ProductVariant setCost(long cost) {
        this.cost = cost;
        return this;
    }

    public long getPrice() {
        return price;
    }

    public ProductVariant setPrice(long price) {
        this.price = price;
        return this;
    }

    public boolean isHot() {
        return hot;
    }

    public ProductVariant setHot(boolean hot) {
        this.hot = hot;
        return this;
    }

    public boolean isAvailable() {
        return available;
    }

    public ProductVariant setAvailable(boolean available) {
        this.available = available;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductVariant that = (ProductVariant) o;
        return Objects.equals(product, that.product) && Objects.equals(productType, that.productType);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
  @Override
  public String toString() {
    return "ProductVariant{"
        + "id="
        + id
        + ", product="
        + product.getName()
        + ", productType="
        + productType.getName()
        + ", cost="
        + cost
        + ", price="
        + price
        + ", hot="
        + hot
        + ", available="
        + available
        + '}';
  }
}
