# User Order API

Enpoint URL Prefix: `/api/account/orders`

## Table of Contents

  - [Lấy danh sách hóa đơn (Không lọc)](#lấy-danh-sách-hóa-đơn-không-lọc)
  - [Lấy danh sách hóa đơn (Lọc theo trạng thái)](#lấy-danh-sách-hóa-đơn-lọc-theo-trạng-thái)
  - [Lấy thông tin chi tiết của một hóa đơn](#lấy-thông-tin-chi-tiết-của-một-hóa-đơn)
  - [Hủy đơn hàng](#hủy-đơn-hàng)

## Lấy danh sách hóa đơn (Không lọc)

---

Lấy danh sách hóa đơn (mọi trạng thái) của user login, mặc định sắp xếp theo mã hóa đơn tăng dần.

- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Request Params:**
    
    
    | Name | Type | Description | Default |
    | --- | --- | --- | --- |
    | page | int | Số thứ tự trang | 0 |
    | size | int | Số lượng hóa đơn muốn lấy | 10 |
- **Success Response:**
    - **Code:** `200 OK`
    - **Body:** Danh sách hóa đơn
    
    **Example:** `GET /api/account/orders?page=1&size=3`
    
    ```json
    [
        {
            "id": 2026,
            "createdAt": "2022-04-21T08:12:20Z",
            "statusId": 1,
            "total": 260000,
            "firstDetail": {
                "productName": "Bánh Cupcake Hoa Hồng đỏsdfds",
                "typeName": "Bộ 9",
                "quantity": 1,
                "unitPrice": 200005,
                "imageUrl": "http://localhost:8080/api/public/image/product/1637281686544.jpg"
            },
            "numRemainingDetails": 2
        },
        {
            "id": 2027,
            "createdAt": "2022-04-21T08:38:06Z",
            "statusId": 1,
            "total": 60000,
            "firstDetail": {
                "productName": "Bánh Cupcake Kiểu Nhật Tua",
                "typeName": "Size M",
                "quantity": 1,
                "unitPrice": 40000,
                "imageUrl": "http://localhost:8080/api/public/image/product/1637281707270.jpg"
            },
            "numRemainingDetails": 1
        },
        {
            "id": 3019,
            "createdAt": "2022-04-21T15:46:16Z",
            "statusId": 1,
            "total": 700000,
            "firstDetail": {
                "productName": "Bánh Cupcake Hoa Hồng đỏsdfds",
                "typeName": "Bộ 9",
                "quantity": 3,
                "unitPrice": 200005,
                "imageUrl": "http://localhost:8080/api/public/image/product/1637281686544.jpg"
            },
            "numRemainingDetails": 1
        }
    ]
    ```
    

## Lấy danh sách hóa đơn (Lọc theo trạng thái)

---

Lấy danh sách hóa đơn (lọc theo trạng thái nhập vào) của user login, mặc định sắp xếp theo mã hóa đơn tăng dần.

- URL: `/status/:statusId` - `statusId` là mã trạng thái cần lọc
- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Request Params:**
    
    
    | Name | Type | Description | Default |
    | --- | --- | --- | --- |
    | page | int | Số thứ tự trang | 0 |
    | size | int | Số lượng hóa đơn muốn lấy | 10 |
- **Success Response:**
    - **Code:** `200 OK`
    - **Body:** Danh sách hóa đơn
    
    **Example:** `GET /api/account/orders/status/1?page=0&size=2`
    
    ```json
    [
        {
            "id": 2023,
            "createdAt": "2022-04-19T17:29:47Z",
            "statusId": 1,
            "total": 960000,
            "firstDetail": {
                "productName": "Bánh Cupcake sinh nhật",
                "typeName": "Bộ 6",
                "quantity": 4,
                "unitPrice": 220000,
                "imageUrl": "http://localhost:8080/api/public/image/product/1637281768558.jpg"
            },
            "numRemainingDetails": 1
        },
        {
            "id": 2024,
            "createdAt": "2022-04-19T17:30:30Z",
            "statusId": 1,
            "total": 350000,
            "firstDetail": {
                "productName": "Bánh Cupcake Cổ điển hương Sô-cô-la",
                "typeName": "Bộ 6",
                "quantity": 1,
                "unitPrice": 150000,
                "imageUrl": "http://localhost:8080/api/public/image/product/1637281717143.jpg"
            },
            "numRemainingDetails": 1
        }
    ]
    ```
    
- **Error Response:**
    - Code `404 NOT_FOUND` - Mã trạng thái (dùng để lọc) không tồn tại

## Lấy thông tin chi tiết của một hóa đơn

---

- URL: `/:orderId` - `orderId`: mã hóa đơn cần xem chi tiết
- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code:** `200 OK`
    - **Body:** Thông tin chi tiết của hóa đơn
    
    **Example:** `GET /api/account/orders/2026`
    
    ```json
    {
        "id": 2026,
        "createdAt": "2022-04-21T08:12:20Z",
        "statusId": 1,
        "total": 260000,
        "canCancel": true,
        "paidByCash": true,
        "receiverInfo": {
            "address": "New Address 2ssdf",
            "phone": "0323456784",
            "note": ""
        },
        "details": [
            {
                "productName": "Bánh Cupcake Hoa Hồng đỏsdfds",
                "typeName": "Bộ 9",
                "quantity": 1,
                "unitPrice": 200000,
                "imageUrl": "http://localhost:8080/api/public/image/product/1637281686544.jpg"
            },
            {
                "productName": "Bánh Cupcake Kiểu Nhật Tua",
                "typeName": "Size M",
                "quantity": 1,
                "unitPrice": 40000,
                "imageUrl": "http://localhost:8080/api/public/image/product/1637281707270.jpg"
            },
            {
                "productName": "Bánh Cupcake Confettidsfsfsdfsf",
                "typeName": "Size L",
                "quantity": 1,
                "unitPrice": 20000,
                "imageUrl": "http://localhost:8080/api/public/image/product/1637281701045.jpg"
            }
        ]
    }
    ```
    
- **Error Response:**
    - Code `400 BAD_REQUEST` - Mã hóa đơn không thuộc về login user
    - Code `404 NOT_FOUND` - Mã hóa đơn không tồn tại

## Hủy đơn hàng

---

Login user hủy đơn hàng thành công khi chỉ khi trạng thái hiện tại của đơn là *Chờ duyệt*

- URL: `/:orderId/cancel` - `orderId`: mã hóa đơn cần xem chi tiết
- **Method:** `PUT`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code:** `200 OK`
    
    **Example:** `PUT /api/account/orders/2026/cancel`
    
- **Error Response:**
    - Code `400 BAD_REQUEST`, entity: `order`, error key: `invalidCancel` - Trạng thái đơn không cho phép hủy
    - Code `400 BAD_REQUEST`, entity: `order`, error key: `notExistedFromAccount`  - Mã hóa đơn không thuộc về login user
    - Code `404 NOT_FOUND` - Mã hóa đơn không tồn tại