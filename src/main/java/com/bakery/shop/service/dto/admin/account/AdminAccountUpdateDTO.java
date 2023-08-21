package com.bakery.shop.service.dto.admin.account;

import java.util.Set;
import javax.validation.constraints.*;

public class AdminAccountUpdateDTO {

    @NotNull
    private Long id;

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "[\\d]{10}")
    private String phone;

    private String address;

    @NotBlank
    private String langKey;

    @NotNull
    @Size(min = 1)
    private Set<String> authorities;

    public AdminAccountUpdateDTO() {}

    public Long getId() {
        return id;
    }

    public AdminAccountUpdateDTO setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public AdminAccountUpdateDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AdminAccountUpdateDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AdminAccountUpdateDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public AdminAccountUpdateDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AdminAccountUpdateDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getLangKey() {
        return langKey;
    }

    public AdminAccountUpdateDTO setLangKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public AdminAccountUpdateDTO setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
        return this;
    }
}
