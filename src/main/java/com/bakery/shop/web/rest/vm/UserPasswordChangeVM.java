package com.bakery.shop.web.rest.vm;

import com.bakery.shop.config.Constants;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/** A DTO representing a password change required data - current and new password. */
public class UserPasswordChangeVM {

    @NotBlank
    @Size(min = Constants.USER_PASSWORD_MIN_LENGTH, max = Constants.USER_PASSWORD_MAX_LENGTH)
    private String currentPassword;

    @NotBlank
    @Size(min = Constants.USER_PASSWORD_MIN_LENGTH, max = Constants.USER_PASSWORD_MAX_LENGTH)
    private String newPassword;

    public UserPasswordChangeVM() {
        // Empty constructor needed for Jackson.
    }

    public UserPasswordChangeVM(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
