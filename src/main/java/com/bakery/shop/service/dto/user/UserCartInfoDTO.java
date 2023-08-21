package com.bakery.shop.service.dto.user;

public class UserCartInfoDTO {

    private long total;
    private long profit;

    public UserCartInfoDTO() {}

    public UserCartInfoDTO(long total, long profit) {
        this.total = total;
        this.profit = profit;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getProfit() {
        return profit;
    }

    public void setProfit(long profit) {
        this.profit = profit;
    }
}
