package com.bakery.shop.domain.order;

import com.bakery.shop.domain.product.ProductVariant;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderDetailId implements Serializable {

    private static final long serialVersionUID = -6274000021052724015L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variantId")
    private ProductVariant variant;

    private OrderDetailId() {}

    public OrderDetailId(Order order, ProductVariant variant) {
        this.order = order;
        this.variant = variant;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductVariant getVariant() {
        return variant;
    }

    public void setVariant(ProductVariant variant) {
        this.variant = variant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailId that = (OrderDetailId) o;
        return order.equals(that.order) && variant.equals(that.variant);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
