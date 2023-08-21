package com.bakery.shop.service.dto.admin.account;

import java.util.Set;
import javax.validation.constraints.*;

public class AdminAccountCreateDTO {

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

    public AdminAccountCreateDTO() {}

    public String getFirstName() {
        return firstName;
    }

    public AdminAccountCreateDTO setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public AdminAccountCreateDTO setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public AdminAccountCreateDTO setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public AdminAccountCreateDTO setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AdminAccountCreateDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getLangKey() {
        return langKey;
    }

    public AdminAccountCreateDTO setLangKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public AdminAccountCreateDTO setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
        return this;
    }
}
