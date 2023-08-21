package com.bakery.shop.domain;

import com.bakery.shop.domain.cart.CartDetail;
import com.bakery.shop.domain.order.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "Account")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "firstName", length = 50)
    private String firstName;

    @NotBlank
    @Column(name = "lastName", length = 50)
    private String lastName;

    @Column(name = "avatar")
    private String imageUrl;

    @Email
    @NotBlank
    @Column(name = "email")
    private String email;

    @Pattern(regexp = "[\\d]{10}")
    @Column(name = "phone", unique = true)
    private String phone;

    @Length(max = 255)
    @Column(name = "address")
    private String address;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    @Column(name = "password", length = 60)
    private String password;

    @NotNull
    @Column(name = "activated")
    private boolean activated = false;

    @JsonIgnore
    @Size(max = 20)
    @Column(name = "activationKey", length = 20)
    private String activationKey;

    @JsonIgnore
    @Size(max = 20)
    @Column(name = "resetKey", length = 20)
    private String resetKey;

    @Column(name = "resetDate")
    private Instant resetDate;

    @Column(name = "langKey")
    private String langKey;

    @Column(name = "lastPasswordChangeDate")
    private LocalDateTime lastPasswordChangeDate;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
        name = "AccountsAuthorities",
        joinColumns = { @JoinColumn(name = "accountId", referencedColumnName = "id") },
        inverseJoinColumns = { @JoinColumn(name = "authorityName", referencedColumnName = "name") }
    )
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @BatchSize(size = 20)
    private Set<Authority> authorities = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "orderedBy", cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "id.account", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CartDetail> cartDetails = new ArrayList<>();

    public Account() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<CartDetail> getCartDetails() {
        return cartDetails;
    }

    public void setCartDetails(List<CartDetail> cartDetails) {
        this.cartDetails = cartDetails;
    }

    public LocalDateTime getLastPasswordChangeDate() {
        return lastPasswordChangeDate;
    }

    public void setLastPasswordChangeDate(LocalDateTime lastPasswordChangeDate) {
        this.lastPasswordChangeDate = lastPasswordChangeDate;
    }

    public void addAuthority(Authority authority) {
        authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        authorities.remove(authority);
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setOrderedBy(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setOrderedBy(null);
    }

    public void addCartDetail(CartDetail cartDetail) {
        cartDetails.add(cartDetail);
        cartDetail.getId().setAccount(this);
    }

    public void removeCartDetail(CartDetail cartDetail) {
        cartDetails.remove(cartDetail);
        // Uncomment will catch Hibernate state stale exception. See: https://vladmihalcea.com/orphanremoval-jpa-hibernate/
        // cartDetail.getId().setAccount(null);
    }

    public void removeAllCartDetails() {
        cartDetails.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }
        return id != null && id.equals(((Account) o).getId());
    }

    @Override
    public int hashCode() {
        // see
        // https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "Account{" +
            "id=" + id +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", email='" + email + '\'' +
            ", phone='" + phone + '\'' +
            ", address='" + address + '\'' +
            ", activated=" + activated +
            ", activationKey='" + activationKey + '\'' +
            ", resetKey='" + resetKey + '\'' +
            ", resetDate=" + resetDate +
            ", langKey='" + langKey + '\'' +
            ", lastPasswordChangeDate=" + lastPasswordChangeDate +
            '}';
    }
}
