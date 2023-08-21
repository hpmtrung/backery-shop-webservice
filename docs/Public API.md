# Public API

Enpoint URL Prefix: `/api/public`

## Table of Contents

  - [Lấy danh sách thể loại sản phẩm (Category)](#lấy-danh-sách-thể-loại-sản-phẩm-category)
  - [Lấy danh sách sản phẩm của một thể loại](#lấy-danh-sách-sản-phẩm-của-một-thể-loại)
  - [Lấy danh sách loại sản phẩm](#lấy-danh-sách-loại-sản-phẩm)
  - [Lấy ảnh thể loại, sản phẩm, user](#lấy-ảnh-thể-loại-sản-phẩm-user)
  - [Thanh toán giỏ hàng (Với user không cần tài khoản)](#thanh-toán-giỏ-hàng-với-user-không-cần-tài-khoản)

## Lấy danh sách thể loại sản phẩm (Category)

- **URL:** `/categories`
- **Method:** `GET`
- **Success Response:**
    - **Code** `200 OK`
    - **Body**: Danh sách thể loại sản phẩm
    
    **Example:** `GET /api/public/categories`
    
    ```json
    [
        {
            "id": 1,
            "name": "Cupcake",
            "imageUrl": "http://localhost:8080/api/public/image/category/1.jpg",
            "icon": "http://localhost:8080/api/public/image/category/1-icon.png",
            "banner": "http://localhost:8080/api/public/image/category/1-banner.png"
        },
        {
            "id": 2,
            "name": "Cheesecake",
            "imageUrl": "http://localhost:8080/api/public/image/category/2.jpg",
            "icon": "http://localhost:8080/api/public/image/category/2-icon.png",
            "banner": "http://localhost:8080/api/public/image/category/2-banner.png"
        },
        {
            "id": 3,
            "name": "Icebox",
            "imageUrl": "http://localhost:8080/api/public/image/category/3.jpg",
            "icon": "http://localhost:8080/api/public/image/category/3-icon.png",
            "banner": "http://localhost:8080/api/public/image/category/3-banner.png"
        },
        //...
    ]
    ```
    

## Lấy danh sách sản phẩm của một thể loại

---

Trả về danh sách sản phẩm của 1 thể loại, mặc định tăng dần theo mã sản phẩm

- **URL:** `/categories/:id/products` - `id` là mã của thể loại
- **Method:** `GET`
- **Request Params:**
    
    
    | Name | Type | Description | Default |
    | --- | --- | --- | --- |
    | page | int | Số thứ tự trang | 0 |
    | size | int | Số lượng sản phẩm muốn lấy | 10 |
- **Success Response:**
    - **Code** `200 OK`
    - **Response Body**: Danh sách sản phẩm của thể loại
    
    **Example:** `GET /api/public/categories/1/products?page=0&size=3`
    
    Response Body:
    
    ```json
    [
        {
            "id": 5,
            "name": "Bánh Cupcake Halloween",
            "ingredients": "Đường cát, Bột ca cao, Bột bánh, Baking Soda, Muối, Bơ, Trứng, Sữa, Kem chua, Chiết xuất Vanilla, Chất tạo màu thực phẩm, Bột mì, Dầu Canola, Bột ca cao, Bột nở, Hạt Sô-cô-la 65%, Đường bánh kẹo, Bông đường, Lòng trắng trứng",
            "allergens": "Lúa mì, Sữa, Trứng, Sản phẩm bơ sữa, Chất Gluten",
            "imageUrls": [
                "http://localhost:8080/api/public/image/product/1637281723604.jpg"
            ],
            "variants": [
                {
                    "id": 14,
                    "typeName": "Bộ 9",
                    "unitPrice": 200000
                }
            ]
        },
        {
            "id": 6,
            "name": "Bánh Cupcake Nhung đỏ",
            "ingredients": "Đường cát, Bột ca cao, Bột bánh, Baking Soda, Muối, Bơ, Trứng, Sữa, Kem chua, Giấm rượu táo, Chiết xuất Vanilla, Chất tạo màu thực phẩm, Bột mì",
            "allergens": null,
            "imageUrls": [
                "http://localhost:8080/api/public/image/product/1637281734150.jpg",
                "http://localhost:8080/api/public/image/product/1637281743270.jpg"
            ],
            "variants": [
                {
                    "id": 15,
                    "typeName": "Bộ 6",
                    "unitPrice": 150000
                },
                {
                    "id": 16,
                    "typeName": "Bộ 9",
                    "unitPrice": 170000
                }
            ]
        }
    ]
    ```
    
- **Error Response:**
    - Code `404 NOT_FOUND` - Mã thể loại không tồn tại
    
## Lấy danh sách loại sản phẩm

---

Trả về danh sách loại của sản phẩm (Size M, Size L,…).

- **URL**: `/product-types`
- **Method**: `GET`
- **Success Response**:
    - **Code**: `200 OK`
    - **Body**: Danh sách loại sản phẩm
    
    **Example**: `GET /api/public/product-types`
    
    **Response Body:**
    
    ```jsx
    [
        {
            "id": 6,
            "name": "Bộ 1"
        },
        {
            "id": 4,
            "name": "Bộ 6"
        },
        {
            "id": 5,
            "name": "Bộ 9"
        },
    		//...
    ]
    ```


## Lấy ảnh thể loại, sản phẩm, user

---

Trả về tài nguyên ảnh theo yêu cầu cần lấy theo thể loại, sản phẩm hoặc user.

- **URL:** `/image/:entity/:fileName`
    - `entity`: kiểu String, có giá trị hoặc `category`, hoặc `product`, hoặc `user`
    - `fileName`: kiểu String, là tên file bao gồm cả phần mở rộng.
- **Method:** `GET`
- **Success Response:**
    - **Code** `200 OK`
    - **Body**: Tài nguyên lưu tại client
    
    **Example**: `GET /api/public/image/product/1637281723604.jpg`
    
- **Error Response:**
    - Code `404 NOT_FOUND` - `entity` không hợp lệ hoặc file không tồn tại
    

## Thanh toán giỏ hàng (Với user không cần tài khoản)

---

- **URL:** `/checkout`
- **Method:** `POST`
- **Body:** Thông tin dùng để thanh toán hóa đơn, tương tự với TH user có tài khoản như cần thêm mảng lưu các item trong giỏ hàng
    
    
    | Field | Type | Description | Constraint |
    | --- | --- | --- | --- |
    | address | String | Địa chỉ nhận hàng | Không trống (NotBlank) |
    | phone | String | SĐT đặt hàng | Không trống (NotBlank), đúng 10 chữ số (Pattern) |
    | paidByCash | boolean | Biến true/false thanh toán bằng tiền mặt hoặc thẻ |  |
    | note | String | Ghi chú |  |
    | cartDetails | CartDetail[] | Mảng chứa sản phẩm trong giỏ hàng | Không trống (NotNull) và chứa ít nhất 1 sản phẩm (Size) |
    
    Về kiểu dữ liệu CartDetail như sau
    
    | Field | Type | Description | Constraint |
    | --- | --- | --- | --- |
    | variantId | int | Mã sản phẩm | Không trống (NotNull) |
    | quantity | int | Số lượng của sản phẩm trong giỏ | Ít nhất 1 (Min) |
    
    *Để nắm rõ, xem ví dụ bên dưới*
    
- **Success Response:**
    
    **Response Body:** Tương tự TH user có tài khoản
    
    **Example:** `POST /api/public/checkout`
    
    **Request Body:**
    
    ```json
    {
        "address": "123 Hai Ba Trung",
        "phone": "0122122551",
        "paidByCash": true,
        "note": "khong",
        "cartDetails": [
            {
                "variantId": 4,
                "quantity": 1
            },
            {
                "variantId": 7,
                "quantity": 2
            }
        ]
    }
    ```
    
    Response Body:
    
    ```json
    {
        "orderId": 4020
    }
    ```
    
- **Error Response:**
    - Code `404 NOT_FOUND` - Có ít nhất 1 sản phẩm có mã `variantId` không tồn tại