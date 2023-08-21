package com.bakery.shop.service;

import com.bakery.shop.domain.Account;
import com.bakery.shop.domain.product.ProductImage;
import com.bakery.shop.service.exceptions.FileNotFoundException;
import com.bakery.shop.service.exceptions.FileServiceException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private static final Path rootUpLoadDir = Paths.get("upload");
    private static final Path userImageUploadDir = Paths.get(rootUpLoadDir.toString(), "user-img");
    private static final Path productImageUploadDir = Paths.get(rootUpLoadDir.toString(), "product-img");
    private static final Path categoryImageUploadDir = Paths.get(rootUpLoadDir.toString(), "category-img");

    private final Logger log = LogManager.getLogger(FileService.class);

    @Autowired
    public FileService() {
        if (!Files.exists(userImageUploadDir)) {
            try {
                Files.createDirectories(userImageUploadDir);
                log.warn("Couldn't find user image upload directory. Create a new one");
            } catch (IOException e) {
                throw new FileServiceException("Failed to create an empty user image upload directory");
            }
        }
        if (!Files.exists(productImageUploadDir)) {
            try {
                Files.createDirectories(productImageUploadDir);
                log.warn("Couldn't find product image upload directory. Create a new one");
            } catch (IOException e) {
                throw new FileServiceException("Failed to create an empty product image upload directory");
            }
        }
    }

    public static String getUserImageFileNameFromAccount(@NotNull Account account, @NotBlank String fileExtension) {
        if (!fileExtension.contains(".")) {
            throw new IllegalArgumentException("File extension is invalid");
        }
        return account.getId() + fileExtension;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveProductImage(MultipartFile productImage, String intendedFileName) {
        if (productImage.isEmpty()) {
            throw new FileServiceException("Couldn't save empty product image file");
        }
        Path destinationFile = productImageUploadDir.resolve(Paths.get(intendedFileName)).normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(productImageUploadDir.toAbsolutePath())) {
            throw new FileServiceException("Couldn't store product image file outside saving directory");
        }

        try (InputStream inputStream = productImage.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileServiceException("Failed to save product image file");
        }
        log.debug("Save product image has name {}", intendedFileName);
    }

    @Transactional(readOnly = true)
    public void deleteProductImage(ProductImage productImage) throws FileServiceException {
        if (productImage == null) {
            throw new IllegalArgumentException("Product image must not be null");
        }

        Path destinationFile = productImageUploadDir.resolve(Paths.get(productImage.getImagePath())).normalize().toAbsolutePath();

        if (!Files.exists(destinationFile)) {
            return;
        }
        try {
            Files.delete(destinationFile);
        } catch (IOException e) {
            throw new FileServiceException("Failed to delete product image file");
        }
        log.debug("Delete product image has name {}", productImage.getImagePath());
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void saveUserImage(MultipartFile userImage, String intendedFileName) {
        if (userImage.isEmpty()) {
            throw new FileServiceException("Couldn't save empty user image file");
        }

        Path destinationFile = userImageUploadDir.resolve(Paths.get(intendedFileName)).normalize().toAbsolutePath();

        if (!destinationFile.getParent().equals(userImageUploadDir.toAbsolutePath())) {
            throw new FileServiceException("Couldn't store user image file outside saving directory");
        }

        try (InputStream inputStream = userImage.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileServiceException("Failed to save user image file");
        }

        log.debug("Save user image with name {}", intendedFileName);
    }

    @Transactional(readOnly = true)
    public void deleteUserImage(Account user) throws FileServiceException {
        if (user == null) {
            throw new IllegalArgumentException("User image must not be null");
        }

        Path destinationFile = userImageUploadDir.resolve(Paths.get(user.getImageUrl())).normalize().toAbsolutePath();

        if (!Files.exists(destinationFile)) {
            return;
        }
        try {
            Files.delete(destinationFile);
        } catch (IOException e) {
            throw new FileServiceException("Failed to delete user image file");
        }
        log.debug("Delete user image has name {}", user.getImageUrl());
    }

    private Resource loadAsResource(Path dir, String fileName) {
        try {
            Path file = dir.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileNotFoundException("Could not find file " + fileName);
            }
        } catch (MalformedURLException e) {
            throw new FileNotFoundException("Could not find file " + fileName, e);
        }
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Resource loadCategoryImageAsResource(String fileName) {
        return loadAsResource(categoryImageUploadDir, fileName);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Resource loadProductImageAsResource(String fileName) {
        return loadAsResource(productImageUploadDir, fileName);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Resource loadUserImageAsResource(String fileName) {
        return loadAsResource(userImageUploadDir, fileName);
    }
}
