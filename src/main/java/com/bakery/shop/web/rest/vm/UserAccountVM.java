package com.bakery.shop.web.rest.vm;

import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/** View model representing a user's account. Used by user */
public class UserAccountVM {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "[\\d]{10}")
    private String phone;

    private String imageUrl;

    private String address;

    private String langKey;

    private boolean activated;

    private Set<String> authorities;

    public UserAccountVM() {}

    public String getFirstName() {
        return firstName;
    }

    public UserAccountVM setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public UserAccountVM setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public UserAccountVM setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public UserAccountVM setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public UserAccountVM setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public UserAccountVM setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getLangKey() {
        return langKey;
    }

    public UserAccountVM setLangKey(String langKey) {
        this.langKey = langKey;
        return this;
    }

    public boolean isActivated() {
        return activated;
    }

    public UserAccountVM setActivated(boolean activated) {
        this.activated = activated;
        return this;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public UserAccountVM setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
        return this;
    }

    @Override
    public String toString() {
        return (
            "UserAccountVM{" +
            "firstName='" +
            firstName +
            '\'' +
            ", lastName='" +
            lastName +
            '\'' +
            ", email='" +
            email +
            '\'' +
            ", phone='" +
            phone +
            '\'' +
            ", imageUrl='" +
            imageUrl +
            '\'' +
            ", address='" +
            address +
            '\'' +
            ", langKey='" +
            langKey +
            '\'' +
            ", activated=" +
            activated +
            ", authorities=" +
            authorities +
            '}'
        );
    }
}
