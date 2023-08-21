package com.bakery.shop.service.exceptions;

public class FileNotFoundException extends FileServiceException {

    private static final long serialVersionUID = -2358846780678142725L;

    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
