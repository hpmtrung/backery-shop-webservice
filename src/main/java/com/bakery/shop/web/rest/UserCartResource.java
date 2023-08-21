package com.bakery.shop.web.rest;

import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.product.ProductVariant;
import com.bakery.shop.repository.product.ProductVariantRepository;
import com.bakery.shop.service.AccountService;
import com.bakery.shop.service.CartService;
import com.bakery.shop.service.dto.user.UserCheckOutFinishDTO;
import com.bakery.shop.service.dto.user.UserCheckOutInitDTO;
import com.bakery.shop.web.rest.errors.ErrorEntityValidationException;
import com.bakery.shop.web.rest.errors.VariantIdNotFoundException;
import com.bakery.shop.web.rest.vm.UserCartVM;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/account/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserCartResource {

    private final AccountService accountService;
    private final CartService cartService;
    private final ProductVariantRepository variantRepository;

    public UserCartResource(AccountService accountService, CartService cartService, ProductVariantRepository variantRepository) {
        this.accountService = accountService;
        this.cartService = cartService;
        this.variantRepository = variantRepository;
    }

    @GetMapping
    public UserCartVM getAccountCart() {
        return cartService.getLoginAccountCart();
    }

    @PostMapping("/item/{variantId}/{quantity}")
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = { Throwable.class })
    public void saveCartItem(@PathVariable("variantId") Integer variantId, @PathVariable("quantity") Integer quantity) {
        Account loginUser = accountService.getLoginAccount().orElseThrow();
        ProductVariant variant = variantRepository.findById(variantId).orElseThrow(VariantIdNotFoundException::new);
        if (quantity < 0) {
            throw new ErrorEntityValidationException("Item quantity is invalid", "cart", "invalidItemQuantity");
        }
        if (
            quantity == 0 &&
            loginUser.getCartDetails().stream().noneMatch(cartDetail -> cartDetail.getId().getVariant().getId().equals(variantId))
        ) {
            throw new ErrorEntityValidationException("Variant is not exist in user cart", "cart", "invalidDeleteItem");
        }
        cartService.saveCartItem(loginUser, variantId, quantity);
    }

    @PostMapping("/checkout")
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = { Throwable.class })
    public UserCheckOutFinishDTO checkOutAuthenticatedUser(@Valid @RequestBody UserCheckOutInitDTO checkOutInitDTO) {
        Account loginUser = accountService.getLoginAccount().orElseThrow();
        if (loginUser.getCartDetails().isEmpty()) {
            throw new ErrorEntityValidationException("Login user cart is empty", "cart", "emptyCheckout");
        }

        return cartService.checkoutAuthenticatedUser(loginUser, checkOutInitDTO);
    }
}
