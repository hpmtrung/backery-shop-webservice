package com.bakery.shop.service;

import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.cart.CartDetail;
import com.bakery.shop.domain.order.Order;
import com.bakery.shop.domain.order.OrderDetail;
import com.bakery.shop.domain.order.OrderStatusConstants;
import com.bakery.shop.domain.product.ProductVariant;
import com.bakery.shop.repository.AccountRepository;
import com.bakery.shop.repository.order.OrderRepository;
import com.bakery.shop.repository.order.OrderStatusRepository;
import com.bakery.shop.repository.product.ProductVariantRepository;
import com.bakery.shop.service.dto.PublicCheckOutInitDTO;
import com.bakery.shop.service.dto.user.UserCheckOutFinishDTO;
import com.bakery.shop.service.dto.user.UserCheckOutInitDTO;
import com.bakery.shop.web.rest.ResourceUtil;
import com.bakery.shop.web.rest.vm.UserCartItemVM;
import com.bakery.shop.web.rest.vm.UserCartVM;
import java.time.Instant;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final OrderRepository orderRepository;
    private final ProductVariantRepository variantRepository;

    public CartService(
        AccountService accountService,
        AccountRepository accountRepository,
        OrderStatusRepository orderStatusRepository,
        OrderRepository orderRepository,
        ProductVariantRepository variantRepository
    ) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.orderRepository = orderRepository;
        this.variantRepository = variantRepository;
    }

    @Transactional(readOnly = true)
    public UserCartVM getLoginAccountCart() {
        Account account = accountService.getLoginAccount().orElseThrow();

        final String productImageURLPrefix = ResourceUtil.getProductImageURLPrefix();

        UserCartVM userCartVM = new UserCartVM();
        userCartVM.setItems(
            account
                .getCartDetails()
                .stream()
                .map(cartDetail -> {
                    final ProductVariant variant = cartDetail.getId().getVariant();
                    return new UserCartItemVM()
                        .setVariantId(variant.getId())
                        .setProductId(variant.getProduct().getId())
                        .setProductName(variant.getProduct().getName())
                        .setCategoryId(variant.getProduct().getCategory().getId())
                        .setTypeName(variant.getProductType().getName())
                        .setUnitPrice(variant.getPrice())
                        .setQuantity(cartDetail.getQuantity())
                        .setImageUrl(productImageURLPrefix + variant.getProduct().getImages().get(0).getImagePath());
                })
                .collect(Collectors.toList())
        );
        return userCartVM;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = { Throwable.class })
    public void saveCartItem(Account account, int variantId, int quantity) {
        CartDetail cartDetail = account
            .getCartDetails()
            .stream()
            .filter(item -> item.getId().getVariant().getId().equals(variantId))
            .findFirst()
            .orElse(null);
        if (cartDetail != null) {
            if (quantity != 0) {
                cartDetail.setQuantity(quantity);
            } else {
                account.removeCartDetail(cartDetail);
            }
        } else {
            final ProductVariant variant = variantRepository.findById(variantId).orElseThrow();
            cartDetail = new CartDetail(account, variant);
            cartDetail.setQuantity(quantity);
            account.addCartDetail(cartDetail);
        }
        accountRepository.saveAndFlush(account);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = { Throwable.class })
    public UserCheckOutFinishDTO checkoutAuthenticatedUser(Account account, UserCheckOutInitDTO userCheckOutInitDTO) {
        final Order order = new Order()
            .setCreatedAt(Instant.now())
            .setAddress(userCheckOutInitDTO.getAddress())
            .setPaidByCash(userCheckOutInitDTO.isPaidByCash())
            .setPhone(userCheckOutInitDTO.getPhone())
            .setStatus(orderStatusRepository.findById(OrderStatusConstants.PROCESSING.getId()).orElseThrow())
            .setNote(userCheckOutInitDTO.getNote());

        long profit = 0L;
        long total = 0L;

        for (CartDetail cartDetail : account.getCartDetails()) {
            final ProductVariant variant = cartDetail.getId().getVariant();
            addNewOrderDetail(order, variant, cartDetail.getQuantity());

            total += cartDetail.getQuantity() * variant.getPrice();
            profit += cartDetail.getQuantity() * (variant.getPrice() - variant.getCost());
        }

        order.setProfit(profit);
        order.setTotal(total);

        account.addOrder(order);
        orderRepository.save(order);

        // Clear account cart
        account.getCartDetails().clear();
        accountRepository.saveAndFlush(account);

        UserCheckOutFinishDTO checkOutFinishDTO = new UserCheckOutFinishDTO();
        checkOutFinishDTO.setOrderId(order.getId());
        return checkOutFinishDTO;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = { Throwable.class })
    public UserCheckOutFinishDTO checkoutUnAuthenticatedUser(PublicCheckOutInitDTO checkOutInitDTO) {
        final Order order = new Order()
            .setCreatedAt(Instant.now())
            .setAddress(checkOutInitDTO.getAddress())
            .setPaidByCash(checkOutInitDTO.isPaidByCash())
            .setPhone(checkOutInitDTO.getPhone())
            .setStatus(orderStatusRepository.findById(OrderStatusConstants.PROCESSING.getId()).orElseThrow())
            .setNote(checkOutInitDTO.getNote());

        checkOutInitDTO.getCartDetails().forEach(cartDetail -> {});

        long profit = 0L;
        long total = 0L;

        for (PublicCheckOutInitDTO.CartDetailVM cartDetail : checkOutInitDTO.getCartDetails()) {
            final ProductVariant variant = variantRepository.findById(cartDetail.getVariantId()).orElseThrow();
            addNewOrderDetail(order, variant, cartDetail.getQuantity());
            total += cartDetail.getQuantity() * variant.getPrice();
            profit += cartDetail.getQuantity() * (variant.getPrice() - variant.getCost());
        }

        order.setProfit(profit);
        order.setTotal(total);

        orderRepository.save(order);

        UserCheckOutFinishDTO checkOutFinishDTO = new UserCheckOutFinishDTO();
        checkOutFinishDTO.setOrderId(order.getId());
        return checkOutFinishDTO;
    }

    private void addNewOrderDetail(Order order, ProductVariant variant, int quantity) {
        OrderDetail newOrderDetail = new OrderDetail(order, variant);
        newOrderDetail.setQuantity(quantity);
        newOrderDetail.setUnitPrice(variant.getPrice());
        order.addOrderDetail(newOrderDetail);
    }
}
