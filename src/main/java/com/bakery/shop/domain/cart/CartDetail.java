package com.bakery.shop.domain.cart;

import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.product.ProductVariant;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

@Entity
@Table(name = "CartDetail")
public class CartDetail implements Serializable {

    private static final long serialVersionUID = 5110161482506098143L;

    @EmbeddedId
    private CartDetailId id;

    @Min(1)
    @Column(name = "quantity")
    private int quantity;

    private CartDetail() {}

    public CartDetail(Account account, ProductVariant productVariant) {
        this.id = new CartDetailId(account, productVariant);
    }

    public CartDetailId getId() {
        return id;
    }

    public void setId(CartDetailId id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartDetail that = (CartDetail) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
