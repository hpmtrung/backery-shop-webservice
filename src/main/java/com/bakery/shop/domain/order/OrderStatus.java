package com.bakery.shop.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.NaturalId;

@Entity(name = "OrderStatus")
@Table(name = "Status")
@org.hibernate.annotations.Immutable
public class OrderStatus implements Serializable {

    private static final long serialVersionUID = -6867373016009928894L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @NotBlank
    @NaturalId
    @Column(name = "name", unique = true)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "status", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setStatus(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setStatus(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderStatus that = (OrderStatus) o;
        return id != null && id.equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
  @Override
  public String toString() {
    return "OrderStatus{" + "id=" + id + ", name='" + name + '\'' + '}';
  }
}
