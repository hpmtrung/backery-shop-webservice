# Admin Variant API

Endpoint URL prefix: `/api/admin/variants`

## Table of Contents

  - [Thêm 1 biến thể cho sản phẩm](#thêm-1-biến-thể-cho-sản-phẩm)
  - [Cập nhật 1 biến thể của sản phẩm](#cập-nhật-1-biến-thể-của-sản-phẩm)
  - [Xóa 1 biến thể của sản phẩm](#xóa-1-biến-thể-của-sản-phẩm)


## Thêm 1 biến thể cho sản phẩm

---

- **Method:** `POST`
- **Header:** `Authorization: Bearer <token>`
- **Request:**
    - **Body:** Chứa thông tin biến thể có dạng như sau
    
    | Field | Type | Description | Constraint |
    | --- | --- | --- | --- |
    | productId | int | Mã sản phẩm cần thêm biến thể | Không trống (NotNull) |
    | typeId | int | Mã loại | Không trống (NotNull) |
    | cost | long | Giá gốc | Ít nhất 1000 (Min) |
    | price | long | Giá bán | Ít nhất 1000 (Min) |
    | hot | boolean | Trạng thái bán chạy |  |
    | available | boolean | Trạng thái còn hàng |  |
- **Success Response:**
    - **Code** `201 CREATED`
    - **Body:** Mã sản phẩm vừa tạo
    
    **Example:** `POST /api/admin/variants`
    
    **Request Body:**
    
    ```json
    {
        "productId": 12,
        "typeId": 1,
        "cost": 20000,
        "price": 40000,
        "hot": false,
        "available": true
    }
    ```
    
    **Response Body:**
    
    ```json
    4092
    ```
    
- **Error Response:**
    - Code `404 NOT_FOUND`, entity: `product`, error key: `notFoundId` - Mã sản phẩm không tồn tại
    - Code `404 NOT_FOUND`, entity: `product`, error key: `notFoundTypeId` - Mã loại không tồn tại
    - Code `400 BAD_REQUEST`, entity: `product`, error key: `invalidCostPriceVariant` - Giá gốc và giá bán không hợp lệ (giá gốc lớn hơn giá bán)
    - Code `400 BAD_REQUEST`, entity: `product`, error key: `existedVariant` - Tồn tại biến thể cùng giá trị `productId` và `typeId`

## Cập nhật 1 biến thể của sản phẩm

---

- **Method:** `PUT`
- **Header:** `Authorization: Bearer <token>`
- **Request:**
    - **Body:** Chứa thông tin biến thể [như trên](Admin%20Variant%20API%204b83f177e52f496f8ed3d0fbb387d82e.md) và mã `id` (kiểu `int`) là mã biến thể cần cập nhật
- **Success Response:**
    - **Code** `200 OK`
    
    **Example:** `PUT /api/admin/variants`
    
    **Request Body:**
    
    ```json
    {
    		"id": 4092,
        "productId": 12,
        "typeId": 1,
        "cost": 20000,
        "price": 40000,
        "hot": false,
        "available": true
    }
    ```
    
- **Error Response:** [như trên](Admin%20Variant%20API%204b83f177e52f496f8ed3d0fbb387d82e.md), và:
    - Code `404 NOT_FOUND`, entity: `product`, error key: `notFoundIdVariant` - Mã biến thể cần cập nhật không tồn tại

## Xóa 1 biến thể của sản phẩm

---

- **URL:** `/:variantId` - `variantId` là mã biến thể cần xóa
- **Method:** `DELETE`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code** `200 OK`
    
    **Example:** `DELETE /api/admin/variants/4092`
    
- **Error Response:**
    - Code `404 NOT_FOUND`, entity: `product`, error key: `notFoundIdVariant` - Mã biến thể cần xóa không tồn tại