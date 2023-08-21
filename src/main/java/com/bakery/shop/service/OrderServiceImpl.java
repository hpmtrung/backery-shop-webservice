package com.bakery.shop.service;

import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.order.Order;
import com.bakery.shop.domain.order.OrderDetail;
import com.bakery.shop.domain.order.OrderStatusConstants;
import com.bakery.shop.domain.product.ProductVariant;
import com.bakery.shop.service.dto.*;
import com.bakery.shop.service.dto.admin.order.AdminOrderDetailDTO;
import com.bakery.shop.service.dto.admin.order.AdminOrderWithDetailsDTO;
import com.bakery.shop.service.dto.admin.order.AdminOverviewOrderDTO;
import com.bakery.shop.service.dto.user.UserOrderDetailDTO;
import com.bakery.shop.service.dto.user.UserOrderWithDetailsDTO;
import com.bakery.shop.service.dto.user.UserOverviewOrderDTO;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class OrderServiceImpl implements OrderService {

    @Override
    public boolean canCancelOrder(Order order) {
        return order.getStatus().getId().equals(OrderStatusConstants.PROCESSING.getId());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UserOverviewOrderDTO getUserOrderOverviewDTOFromOrder(Order order) {
        OrderDetail firstOrderDetail = order.getOrderDetails().get(0);
        ProductVariant variant = firstOrderDetail.getId().getVariant();

        return new UserOverviewOrderDTO()
            .setId(order.getId())
            .setCreatedAt(order.getCreatedAt())
            .setStatusId(order.getStatus().getId())
            .setTotal(order.getTotal())
            .setFirstDetail(
                new UserOrderDetailDTO()
                    .setProductName(variant.getProduct().getName())
                    .setTypeName(variant.getProductType().getName())
                    .setUnitPrice(variant.getPrice())
                    .setImageUrl(variant.getProduct().getImages().get(0).getImagePath())
                    .setQuantity(firstOrderDetail.getQuantity())
            )
            .setNumRemainingDetails(order.getOrderDetails().size() - 1);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UserOrderWithDetailsDTO getUserOrderWithDetailsDTOFromOrder(Order order) {
        return new UserOrderWithDetailsDTO()
            .setId(order.getId())
            .setCreatedAt(order.getCreatedAt())
            .setStatusId(order.getStatus().getId())
            .setTotal(order.getTotal())
            .setCanCancel(canCancelOrder(order))
            .setPaidByCash(order.isPaidByCash())
            .setDetails(
                order
                    .getOrderDetails()
                    .stream()
                    .map(orderDetail -> {
                        ProductVariant variant = orderDetail.getId().getVariant();

                        return new UserOrderDetailDTO()
                            .setProductName(variant.getProduct().getName())
                            .setTypeName(variant.getProductType().getName())
                            .setQuantity(orderDetail.getQuantity())
                            .setUnitPrice(orderDetail.getUnitPrice())
                            .setImageUrl(variant.getProduct().getImages().get(0).getImagePath());
                    })
                    .collect(Collectors.toList())
            )
            .setReceiverInfo(
                new UserOrderWithDetailsDTO.ReceiverInfo()
                    .setAddress(order.getAddress())
                    .setPhone(order.getPhone())
                    .setNote(order.getNote())
            );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public AdminOverviewOrderDTO getAdminOverviewOrderDTOFromOrder(Order order) {
        AdminOverviewOrderDTO adminOverviewOrderDTO = new AdminOverviewOrderDTO()
            .setId(order.getId())
            .setCreatedAt(order.getCreatedAt())
            .setStatusId(order.getStatus().getId())
            .setTotal(order.getTotal());

        final Account customer = order.getOrderedBy();
        if (customer != null) {
            adminOverviewOrderDTO.setCustomerName(customer.getLastName() + " " + customer.getFirstName());
        } else {
            adminOverviewOrderDTO.setCustomerName("Khách");
        }

        return adminOverviewOrderDTO;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public AdminOrderWithDetailsDTO getAdminOrderWithDetailsDTOFromOrder(Order order) {
        AdminOrderWithDetailsDTO adminOrderWithDetailsDTO = new AdminOrderWithDetailsDTO()
            .setId(order.getId())
            .setCreatedAt(order.getCreatedAt())
            .setStatusId(order.getStatus().getId())
            .setTotal(order.getTotal())
            .setCanCancel(canCancelOrder(order))
            .setPaidByCash(order.isPaidByCash())
            .setReceiverInfo(new OrderReceiverDTO().setAddress(order.getAddress()).setPhone(order.getPhone()).setNote(order.getNote()))
            .setDetails(
                order
                    .getOrderDetails()
                    .stream()
                    .map(orderDetail -> {
                        ProductVariant variant = orderDetail.getId().getVariant();

                        return new AdminOrderDetailDTO()
                            .setProductName(variant.getProduct().getName())
                            .setTypeName(variant.getProductType().getName())
                            .setQuantity(orderDetail.getQuantity())
                            .setUnitPrice(orderDetail.getUnitPrice());
                    })
                    .collect(Collectors.toList())
            );

        final Account customer = order.getOrderedBy();
        if (customer != null) {
            adminOrderWithDetailsDTO.setCustomerName(customer.getLastName() + " " + customer.getFirstName());
        } else {
            adminOrderWithDetailsDTO.setCustomerName("Khách");
        }

        return adminOrderWithDetailsDTO;
    }
}
