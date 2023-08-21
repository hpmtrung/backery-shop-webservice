package com.bakery.shop.web.rest.vm;

import com.bakery.shop.config.Constants;
import javax.validation.constraints.*;

public class PublicRegisterVM {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Pattern(regexp = "[\\d]{10}")
    private String phone;

    private String address;

    @NotBlank
    @Size(min = Constants.USER_PASSWORD_MIN_LENGTH, max = Constants.USER_PASSWORD_MAX_LENGTH)
    private String password;

    public PublicRegisterVM() {}

    public String getPassword() {
        return password;
    }

    public PublicRegisterVM setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public PublicRegisterVM setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PublicRegisterVM setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public PublicRegisterVM setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public PublicRegisterVM setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public PublicRegisterVM setAddress(String address) {
        this.address = address;
        return this;
    }
}
