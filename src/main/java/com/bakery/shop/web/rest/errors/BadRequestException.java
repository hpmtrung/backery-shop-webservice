package com.bakery.shop.web.rest.errors;

import com.bakery.shop.web.rest.errors.ErrorConstants;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class BadRequestException extends AbstractThrowableProblem {

    private static final long serialVersionUID = -2868952225044952790L;

    private final String message;

    public BadRequestException(String title, String message) {
        super(ErrorConstants.DEFAULT_TYPE, title, Status.BAD_REQUEST);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
