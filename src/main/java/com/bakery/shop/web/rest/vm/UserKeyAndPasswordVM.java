package com.bakery.shop.web.rest.vm;

import com.bakery.shop.config.Constants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/** View Model object for storing the user's key and password. */
public class UserKeyAndPasswordVM {

    @NotBlank
    private String key;

    @NotBlank
    @Size(min = Constants.USER_PASSWORD_MIN_LENGTH, max = Constants.USER_PASSWORD_MAX_LENGTH)
    private String newPassword;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
