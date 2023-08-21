package com.bakery.shop.service.dto.admin.account;

import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.Authority;
import java.util.Set;
import java.util.stream.Collectors;

/** A DTO representing a user's account, with his authorities. Used by Admin */
public class AdminAccountDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String imageUrl;
    private String address;
    private boolean activated;
    private String langKey;
    private Set<String> authorities;

    public AdminAccountDTO() {
        // Empty constructor needed for Jackson.
    }

    public AdminAccountDTO(Account user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.activated = user.isActivated();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.phone = user.getPhone();
        this.address = user.getAddress();
        this.authorities = user.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public AdminAccountDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AdminAccountDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AdminAccountDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AdminAccountDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public AdminAccountDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public AdminAccountDTO setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AdminAccountDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public AdminAccountDTO setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public String getLangKey() {
        return langKey;
    }

    public AdminAccountDTO setLangKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public AdminAccountDTO setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
        return this;
    }

    // prettier-ignore
  @Override
  public String toString() {
    return "AdminAccountDTO{"
        + "firstName='"
        + firstName
        + '\''
        + ", lastName='"
        + lastName
        + '\''
        + ", email='"
        + email
        + '\''
        + ", imageUrl='"
        + imageUrl
        + '\''
        + ", activated="
        + activated
        + ", langKey='"
        + langKey
        + '\''
        + ", phone ='"
        + phone
        + '\''
        + ", address ='"
        + address
        + '\''
        + ", authorities="
        + authorities
        + "}";
  }
}
