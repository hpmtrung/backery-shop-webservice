package com.bakery.shop.web.rest.vm;

import com.bakery.shop.service.dto.admin.order.AdminStatisticOrderDTO;
import java.util.List;

/** View Model object for admin homepage */
public class AdminHomeStatisticVM {

    private long totalOrdersNum;

    private long todayOrdersNum;

    private long todayProcessingOrdersNum;

    private long todayCancelOrdersNum;

    private long todayDispatchOrdersNum;

    private long todayShippedOrdersNum;

    private long totalAvailableProductVariantsNum;

    private long totalSoldProductVariantsNum;

    private long todaySoldProductVariantsNum;

    private List<AdminStatisticOrderDTO> topRecentOrders;

    public AdminHomeStatisticVM() {}

    public long getTotalOrdersNum() {
        return totalOrdersNum;
    }

    public AdminHomeStatisticVM setTotalOrdersNum(long totalOrdersNum) {
        this.totalOrdersNum = totalOrdersNum;
        return this;
    }

    public long getTodayOrdersNum() {
        return todayOrdersNum;
    }

    public AdminHomeStatisticVM setTodayOrdersNum(long todayOrdersNum) {
        this.todayOrdersNum = todayOrdersNum;
        return this;
    }

    public long getTotalAvailableProductVariantsNum() {
        return totalAvailableProductVariantsNum;
    }

    public AdminHomeStatisticVM setTotalAvailableProductVariantsNum(long totalAvailableProductVariantsNum) {
        this.totalAvailableProductVariantsNum = totalAvailableProductVariantsNum;
        return this;
    }

    public long getTodayProcessingOrdersNum() {
        return todayProcessingOrdersNum;
    }

    public AdminHomeStatisticVM setTodayProcessingOrdersNum(long todayProcessingOrdersNum) {
        this.todayProcessingOrdersNum = todayProcessingOrdersNum;
        return this;
    }

    public long getTodayCancelOrdersNum() {
        return todayCancelOrdersNum;
    }

    public AdminHomeStatisticVM setTodayCancelOrdersNum(long todayCancelOrdersNum) {
        this.todayCancelOrdersNum = todayCancelOrdersNum;
        return this;
    }

    public long getTodayDispatchOrdersNum() {
        return todayDispatchOrdersNum;
    }

    public AdminHomeStatisticVM setTodayDispatchOrdersNum(long todayDispatchOrdersNum) {
        this.todayDispatchOrdersNum = todayDispatchOrdersNum;
        return this;
    }

    public long getTodayShippedOrdersNum() {
        return todayShippedOrdersNum;
    }

    public AdminHomeStatisticVM setTodayShippedOrdersNum(long todayShippedOrdersNum) {
        this.todayShippedOrdersNum = todayShippedOrdersNum;
        return this;
    }

    public long getTotalSoldProductVariantsNum() {
        return totalSoldProductVariantsNum;
    }

    public AdminHomeStatisticVM setTotalSoldProductVariantsNum(long totalSoldProductVariantsNum) {
        this.totalSoldProductVariantsNum = totalSoldProductVariantsNum;
        return this;
    }

    public long getTodaySoldProductVariantsNum() {
        return todaySoldProductVariantsNum;
    }

    public AdminHomeStatisticVM setTodaySoldProductVariantsNum(long todaySoldProductVariantsNum) {
        this.todaySoldProductVariantsNum = todaySoldProductVariantsNum;
        return this;
    }

    public List<AdminStatisticOrderDTO> getTopRecentOrders() {
        return topRecentOrders;
    }

    public AdminHomeStatisticVM setTopRecentOrders(List<AdminStatisticOrderDTO> topRecentOrders) {
        this.topRecentOrders = topRecentOrders;
        return this;
    }
}
