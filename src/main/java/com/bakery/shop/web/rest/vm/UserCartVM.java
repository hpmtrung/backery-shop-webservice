package com.bakery.shop.web.rest.vm;

import java.util.List;

public class UserCartVM {

    private List<UserCartItemVM> items;

    public UserCartVM() {}

    public List<UserCartItemVM> getItems() {
        return items;
    }

    public void setItems(List<UserCartItemVM> items) {
        this.items = items;
    }
}
