package com.bakery.shop.web.rest;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

public class ResourceUtil {

    private ResourceUtil() {}

    public static String getBaseUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }

    public static String getCategoryImageURLPrefix() {
        return getBaseUrl() + "/api/public/image/category/";
    }

    public static String getProductImageURLPrefix() {
        return getBaseUrl() + "/api/public/image/product/";
    }

    public static String getUserImageURLPrefix() {
        return getBaseUrl() + "/api/public/image/user/";
    }

    public static HttpHeaders getHeaderFromPage(Page page) {
        return PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
    }
}
