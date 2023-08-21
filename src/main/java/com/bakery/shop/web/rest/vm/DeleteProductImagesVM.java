package com.bakery.shop.web.rest.vm;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class DeleteProductImagesVM {

    @NotNull
    @Size(min = 1, max = 10)
    private List<Integer> deletedImageIds;

    public DeleteProductImagesVM() {}

    public List<Integer> getDeletedImageIds() {
        return deletedImageIds;
    }

    public DeleteProductImagesVM setDeletedImageIds(List<Integer> deletedImageIds) {
        this.deletedImageIds = deletedImageIds;
        return this;
    }
}
