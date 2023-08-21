package com.bakery.shop.web.rest;

import com.bakery.shop.config.Constants;
import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.Authority;
import com.bakery.shop.repository.AccountRepository;
import com.bakery.shop.service.AccountService;
import com.bakery.shop.service.FileService;
import com.bakery.shop.web.rest.errors.BadRequestException;
import com.bakery.shop.web.rest.errors.EmailAlreadyUsedException;
import com.bakery.shop.web.rest.vm.UserAccountVM;
import com.bakery.shop.web.rest.vm.UserPasswordChangeVM;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api/account", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAccountResource {

    private final Logger log = LoggerFactory.getLogger(UserAccountResource.class);

    private final AccountService accountService;
    private final AccountRepository accountRepository;
    private final FileService fileService;

    public UserAccountResource(AccountService accountService, AccountRepository accountRepository, FileService fileService) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.fileService = fileService;
    }

    /**
     * {@code GET /account} : get the current user.
     *
     * @return the current user.
     * @throws RuntimeException {@code 500 (Internal Server Error)} if the user couldn't be found.
     */
    @GetMapping
    public UserAccountVM getAccount() {
        final String userImageURLPrefix = ResourceUtil.getUserImageURLPrefix();
        return accountService
            .getLoginAccountWithAuthorities()
            .map(account ->
                new UserAccountVM()
                    .setFirstName(account.getFirstName())
                    .setLastName(account.getLastName())
                    .setEmail(account.getEmail())
                    .setPhone(account.getPhone())
                    .setAddress(account.getAddress())
                    .setLangKey(account.getLangKey())
                    .setActivated(account.isActivated())
                    .setImageUrl(userImageURLPrefix + account.getImageUrl())
                    .setAuthorities(account.getAuthorities().stream().map(Authority::getName).collect(Collectors.toSet()))
            )
            .orElseThrow(() -> new UserAccountResourceException("User couldn't be found"));
    }

    @PutMapping("/info")
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
    public void saveAccountInfo(@Valid @RequestBody UserAccountVM userAccountVM) {
        final Account loginUser = accountService.getLoginAccount().orElseThrow();

        // check email should be not duplicated
        final Account existingAccountWithSameEmail = accountRepository.findOneByEmailIgnoreCase(loginUser.getEmail()).orElse(null);
        if (existingAccountWithSameEmail != null && !existingAccountWithSameEmail.getId().equals(loginUser.getId())) {
            throw new EmailAlreadyUsedException();
        }

        accountService.updateAccountByUser(userAccountVM);
    }

    @PutMapping("/image")
    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = Throwable.class)
    public void saveAccountImage(@RequestParam("image") MultipartFile image) {
        final Account loginUser = accountService.getLoginAccount().orElseThrow();

        String originFileName = image.getOriginalFilename();
        if (!Objects.requireNonNull(originFileName).contains(".")) {
            throw new BadRequestException("User upload image name is invalid: '" + originFileName + "'", "invalidUpload");
        }

        // Save updated avatar with name formated in pattern
        String fileName = loginUser.getId() + originFileName.substring(originFileName.lastIndexOf("."));

        String loginUserImage = loginUser.getImageUrl();
        if (loginUserImage != null && !loginUserImage.equals(Constants.DEFAULT_USER_IMAGE_URL)) {
            fileService.deleteUserImage(loginUser);
        }
        fileService.saveUserImage(image, fileName);

        loginUser.setImageUrl(fileName);
        accountRepository.saveAndFlush(loginUser);
    }

    /**
     * {@code POST /account/change-password} : change the current user's password.
     *
     * @param userPasswordChangeVM current and new password.
     */
    @PostMapping("/change-password")
    public void changePassword(@Valid @RequestBody UserPasswordChangeVM userPasswordChangeVM) {
        accountService.changePassword(userPasswordChangeVM.getCurrentPassword(), userPasswordChangeVM.getNewPassword());
    }

    private static class UserAccountResourceException extends RuntimeException {

        private static final long serialVersionUID = 1218642303828771896L;

        private UserAccountResourceException(String message) {
            super(message);
        }
    }
}
