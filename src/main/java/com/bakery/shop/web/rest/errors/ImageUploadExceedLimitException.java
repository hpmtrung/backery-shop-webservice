package com.bakery.shop.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class ImageUploadExceedLimitException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 378642061580706524L;

    public ImageUploadExceedLimitException() {
        super(ErrorConstants.DEFAULT_TYPE, "Image upload exceeds size limitation (10MB)", Status.BAD_REQUEST);
    }
}
