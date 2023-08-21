package com.bakery.shop.domain.cart;

import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.product.ProductVariant;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Embeddable
public class CartDetailId implements Serializable {

    private static final long serialVersionUID = -8521446723538074595L;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", referencedColumnName = "id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "variantId", referencedColumnName = "id")
    private ProductVariant variant;

    public CartDetailId() {}

    public CartDetailId(Account account, ProductVariant variant) {
        this.account = account;
        this.variant = variant;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
        CartDetailId that = (CartDetailId) o;
        return account.equals(that.account) && variant.equals(that.variant);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
