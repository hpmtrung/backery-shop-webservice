package com.bakery.shop.domain.order;

import com.bakery.shop.domain.product.ProductVariant;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "OrderDetail")
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId id;

    @Min(value = 1)
    @Column(name = "quantity")
    private int quantity;

    @Column(name = "unitPrice")
    private long unitPrice;

    private OrderDetail() {}

    public OrderDetail(Order order, ProductVariant productVariant) {
        this.id = new OrderDetailId(order, productVariant);
    }

    public OrderDetailId getId() {
        return id;
    }

    public void setId(OrderDetailId id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
