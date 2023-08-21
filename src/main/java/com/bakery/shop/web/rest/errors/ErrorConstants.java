package com.bakery.shop.web.rest.errors;

import java.net.URI;

public final class ErrorConstants {

    public static final String ERR_UPLOAD_FAILURE = "uploadFailure";
    public static final String ERR_FILE_NOT_FOUND = "fileNotFound";
    public static final String ERR_CONCURRENCY_FAILURE = "concurrencyFailure";
    public static final String ERR_VALIDATION = "validationError";
    public static final String ERROR_BASE_URL = "https://localhost:8080/api/public/docs/error";

    public static final URI DEFAULT_TYPE = URI.create(ERROR_BASE_URL + "/#error-with-message");
    public static final URI ENTITY_TYPE = URI.create(ERROR_BASE_URL + "/#entity");
    public static final URI CONSTRAINT_VIOLATION_TYPE = URI.create(ERROR_BASE_URL + "/#constraint-violation");

    private ErrorConstants() {}
}
