package com.bakery.shop.domain.order;

import com.bakery.shop.domain.Account;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "`Order`")
public class Order implements Serializable {

    private static final long serialVersionUID = 8425753592104815046L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountId", nullable = true)
    private Account orderedBy;

    @NotNull
    @org.hibernate.annotations.CreationTimestamp
    @Column(name = "createdAt", updatable = false)
    private Instant createdAt;

    @Column(name = "total")
    private long total;

    @NotBlank
    @Column(name = "address")
    private String address;

    @NotBlank
    @Column(name = "phone")
    private String phone;

    @Column(name = "paymentMethod")
    private boolean paidByCash;

    @ManyToOne
    @JoinColumn(name = "statusId")
    private OrderStatus status;

    @Length(max = 300)
    @Column(name = "note")
    private String note;

    @Column(name = "profit")
    private long profit;

    @OneToMany(mappedBy = "id.order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails = new ArrayList<>();

    public Order() {}

    public Long getId() {
        return id;
    }

    public Order setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public Order setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public Account getOrderedBy() {
        return orderedBy;
    }

    public Order setOrderedBy(Account orderedBy) {
        this.orderedBy = orderedBy;
        return this;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Order setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public Order setTotal(long total) {
        this.total = total;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Order setAddress(String address) {
        this.address = address;
        return this;
    }

    public boolean isPaidByCash() {
        return paidByCash;
    }

    public Order setPaidByCash(boolean paidByCash) {
        this.paidByCash = paidByCash;
        return this;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Order setStatus(OrderStatus status) {
        this.status = status;
        return this;
    }

    public String getNote() {
        return note;
    }

    public Order setNote(String note) {
        this.note = note;
        return this;
    }

    public long getProfit() {
        return profit;
    }

    public Order setProfit(long profit) {
        this.profit = profit;
        return this;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public Order setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
        return this;
    }

    public void addOrderDetail(OrderDetail orderDetail) {
        this.orderDetails.add(orderDetail);
        orderDetail.getId().setOrder(this);
    }

    public void removeOrderDetail(OrderDetail removedOrderDetail) {
        for (Iterator<OrderDetail> iterator = orderDetails.iterator(); iterator.hasNext();) {
            OrderDetail orderDetail = iterator.next();
            if (orderDetail.getId().getVariant().equals(removedOrderDetail.getId().getVariant())) {
                iterator.remove();
                orderDetail.getId().setOrder(null);
                orderDetail.getId().setVariant(null);
            }
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id != null && id.equals(order.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore

  @Override
  public String toString() {
    return "Order{"
        + "id="
        + id
        + ", orderedBy="
        + (orderedBy != null ? orderedBy.getId() : null)
        + ", createdAt="
        + createdAt
        + ", total="
        + total
        + ", address='"
        + address
        + '\''
        + ", phone='"
        + phone
        + '\''
        + ", paidByCash="
        + paidByCash
        + ", status="
        + status
        + ", note='"
        + note
        + '\''
        + ", profit="
        + profit
        + '}';
  }
}
