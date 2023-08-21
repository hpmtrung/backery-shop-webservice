# User Checkout API

Endpoint URL Prefix: `api/account/cart`

## Table of Contents

  - [Lấy thông tin giỏ hàng](#lấy-thông-tin-giỏ-hàng)
  - [Cập nhật sản phẩm trong giỏ hàng (Thêm, xóa, sửa)](#cập-nhật-sản-phẩm-trong-giỏ-hàng-thêm-xóa-sửa)
  - [Thanh toán giỏ hàng](#thanh-toán-giỏ-hàng)
  

## Lấy thông tin giỏ hàng

---

- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code** `200 OK`
    - **Body:** Thông tin giỏ hàng có dạng như sau:
        
        ```json
        {
            "items": [
                {
                    "variantId": 7,
                    "productId": 3,
                    "productName": "Bánh Cupcake Kiểu Nhật Tua",
                    "categoryId": 5,
                    "typeName": "Size M",
                    "unitPrice": 40000,
                    "quantity": 3,
                    "imageUrl": "http://localhost:8080/api/public/image/product/1637281707270.jpg"
                },
                {
                    "variantId": 2093,
                    "productId": 2,
                    "productName": "Bánh Cupcake Confettidsfsfsdfsf",
                    "categoryId": 3,
                    "typeName": "Size L",
                    "unitPrice": 20000,
                    "quantity": 1,
                    "imageUrl": "http://localhost:8080/api/public/image/product/1637281701045.jpg"
                }
            ]
        }
        ```
        

## Cập nhật sản phẩm trong giỏ hàng (Thêm, xóa, sửa)

---

Các sản phẩm trong giỏ hàng được cập nhật (thêm, xóa, sửa) chỉ thông qua duy nhất endpoint này. Cách thức như sau:

- Nếu `variantId` không tồn tại trong giỏ hàng, sản phẩm được *thêm* vào ứng với số lượng `quantity`
- Nếu `variantId` tồn tại trong giỏ hàng, khi `quantity` lớn hơn 0 sản phẩm được cập nhật số lượng mới, ngược lại khi `quantity` bằng 0 thì sản phẩm bị xóa đi.

- **URL:** `/item/:variantId/:quantity`
    - `variantId`: mã biến thể
    - `quantity`: số lượng
- **Method:** `POST`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code** `200 OK`
    
    **Example:** `POST /api/account/cart/item/14/2`
    
- **Error Response:**
    - Code `404 NOT_FOUND` - Mã sản phẩm `variantId` không tồn tại.
    - Code `400 BAD_REQUEST`, entity: `cart`, error key: `invalidItemQuantity` - Số lượng `quantity` không hợp lệ
    - Code `400 BAD_REQUEST`, entity: `cart`, error key: `invalidDeleteItem` - Sản phẩm muốn xóa (đặt `quantity` là 0) không tồn tại trong giỏ hàng của user

## Thanh toán giỏ hàng

---

Điều kiện để thanh toán không bị lỗi là giỏ hàng khác rỗng. Sau khi thanh toán thành công, giỏ hàng về trạng thái rỗng.

- **URL:** `/checkout`
- **Method:** `POST`
- **Header:** `Authorization: Bearer <token>`
- **Body:** Thông tin dùng để thanh toán hóa đơn, có dạng:
    
    
    | Field | Type | Description | Constraint |
    | --- | --- | --- | --- |
    | address | String | Địa chỉ nhận hàng | Không trống (NotBlank) |
    | phone | String | SĐT đặt hàng | Không trống (NotBlank), đúng 10 chữ số (Pattern) |
    | paidByCash | boolean | Biến true/false thanh toán bằng tiền mặt hoặc thẻ |  |
    | note | String | Ghi chú |  |
- **Success Response:**
    - **Code** `200 OK`
    - **Body:** Thông tin thông báo cho user về tình trạng đơn hàng, có dạng:
        
        
        | Field | Type | Description |
        | --- | --- | --- |
        | orderId | long | Mã hóa đơn thanh toán thành công |
    
    **Example:** `POST /api/account/cart/checkout`
    
    Request Body:
    
    ```json
    {
        "address": "123 Hai Ba Trung",
        "phone": "0122114521",
        "paidByCash": true,
        "note": "khong"
    }
    ```
    
    Response Body:
    
    ```json
    {
        "orderId": 4019
    }
    ```
    
- **Error Response:**
    - Code `400 BAD_REQUEST` - Đơn hàng rỗng