package com.bakery.shop.web.rest.vm;

import com.bakery.shop.config.Constants;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * View Model object for storing a user's credentials.
 */
public class PublicLoginVM {

    @NotNull
    @Email
    private String email;

    @NotBlank
    @Size(min = Constants.USER_PASSWORD_MIN_LENGTH, max = Constants.USER_PASSWORD_MAX_LENGTH)
    private String password;

    private boolean rememberMe;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PublicLoginVM{" +
            "email='" + email + '\'' +
            ", rememberMe=" + rememberMe +
            '}';
    }
}
