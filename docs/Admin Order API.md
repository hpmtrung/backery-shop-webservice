# Admin Order API

Endpoint URL prefix: `/api/admin/orders`

## Table of Contents

  - [Lấy danh sách thông tin cơ bản hóa đơn](#lấy-danh-sách-thông-tin-cơ-bản-hóa-đơn)
  - [Lấy danh sách thông tin cơ bản hóa đơn của một tài khoản](#lấy-danh-sách-thông-tin-cơ-bản-hóa-đơn-của-một-tài-khoản)
  - [Lấy thông tin chi tiết của một hóa đơn](#lấy-thông-tin-chi-tiết-của-một-hóa-đơn)
  - [Cập nhật trạng thái hóa đơn lên 1 cấp](#cập-nhật-trạng-thái-hóa-đơn-lên-1-cấp)
  - [Hủy đơn hàng](#hủy-đơn-hàng)

## Lấy danh sách thông tin cơ bản hóa đơn

---

Trả về danh sách thông tin cơ bản của hóa đơn, mặc định sắp xếp theo mã hóa đơn tăng dần

- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Request Params:**
    
    
    | Name | Type | Description | Default |
    | --- | --- | --- | --- |
    | page | int | Số thứ tự trang | 0 |
    | size | int | Số lượng hóa đơn muốn lấy | 10 |
- **Success Response:**
    - **Code** `200 OK`
    - **Body:** Danh sách hóa đơn
    
    **Example:** `GET api/admin/orders?page=2&size=5`
    
    **Response Body:**
    
    ```json
    [
        {
            "id": 2,
            "createdAt": "2021-11-08T17:37:38Z",
            "statusId": 4,
            "total": 1,
            "customerName": "Nam Hoàng"
        },
        {
            "id": 3,
            "createdAt": "2021-11-08T18:16:54Z",
            "statusId": 3,
            "total": 1,
            "customerName": "Nam Hoàng"
        },
        {
            "id": 4,
            "createdAt": "2021-11-08T23:48:21Z",
            "statusId": 3,
            "total": 1,
            "customerName": "Nam Hoàng"
        }
    ]
    ```
    

## Lấy danh sách thông tin cơ bản hóa đơn của một tài khoản

---

Trả về danh sách thông tin cơ bản của hóa đơn của một tài khoản bất kì, mặc định sắp xếp theo mã hóa đơn tăng dần

- **URL:** `/account/:accountId` - `accountId` là mã tài khoản cần lấy hóa đơn
- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Request Params:**
    
    
    | Name | Type | Description | Default |
    | --- | --- | --- | --- |
    | page | int | Số thứ tự trang | 0 |
    | size | int | Số lượng hóa đơn muốn lấy | 10 |
- **Success Response:**
    - **Code** `200 OK` - Kèm danh sách hóa đơn
    
    **Example:** `GET /api/admin/orders/account/6?page=0&size=2`
    
    **Body:**
    
    ```json
    [
        {
            "id": 1019,
            "createdAt": "2022-04-15T12:04:16Z",
            "statusId": 1,
            "total": 940000,
            "customerName": "Websd Users"
        },
        {
            "id": 2023,
            "createdAt": "2022-04-19T17:29:47Z",
            "statusId": 1,
            "total": 960000,
            "customerName": "Websd Users"
        }
    ]
    ```
    
- **Error Response:**
    - Code `404 NOT_FOUND` - Mã tài khoản không tồn tại

## Lấy thông tin chi tiết của một hóa đơn

---

Trả về thông tin cơ bản và các chi tiết trên hóa đơn theo mã hóa đơn

- **URL:** `/:orderId` - `orderId` là mã hóa đơn
- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code** `200 OK` - Kèm thông tin hóa đơn
    
    **Example:** `GET /api/admin/orders/12`
    
    **Response Body:**
    
    ```json
    {
        "id": 12,
        "createdAt": "2021-12-10T01:01:52Z",
        "statusId": 4,
        "total": 760000,
        "customerName": "web Me",
        "canCancel": false,
        "paidByCash": false,
        "receiverInfo": {
            "address": "sv dd, Phường Đồng Xuân, Quận Hoàn Kiếm, Thành phố Hà Nội",
            "phone": "0123456789",
            "note": ""
        },
        "details": [
            {
                "productName": "Bánh Pudding chuối Sô-cô-la hạt phỉ",
                "typeName": "Size S",
                "quantity": 1,
                "unitPrice": 360000
            },
            {
                "productName": "Bánh Pudding chuối nhung đỏ",
                "typeName": "Size M",
                "quantity": 1,
                "unitPrice": 400000
            }
        ]
    }
    ```
    
- **Error Response:**
    - Code `404 NOT_FOUND` - Mã hóa đơn không tồn tại

## Cập nhật trạng thái hóa đơn lên 1 cấp

---

Trạng thái hóa đơn có thể được cập nhật lên 1 cấp, bắt đầu từ *Chờ duyệt* theo quy tắc Chờ duyệt → Đang giao → Đã giao. Một lần cập nhật chỉ lên 1 cấp, điều này đảm bảo tính logic cho trạng thái hóa đơn.

(**Lưu ý**: Không xét trạng thái Hủy đơn, chi tiết xem [endpoint hủy đơn](Admin%20Order%20API%20a7c55f9677e24b87bc61f82d3bb76628.md))

- **URL:** `/:orderId/status/update` - `orderId` là mã hóa đơn cần cập nhật trạng thái
- **Method:** `PUT`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code** `200 OK`
    - **Body:** Thông tin trạng thái hóa đơn sau cập nhật như sau
        
        
        | Field | Type | Description |
        | --- | --- | --- |
        | orderId | long | Mã hóa đơn |
        | updatedStatusId | int | Mã trạng thái sau cập nhật |
        | updatedStatusName | String | Tên trạng thái sau cập nhật |
        | canCancel | boolean | Biến true/false chỉ hóa đơn có thể hủy hay không |
    
    **Example:** `PUT /api/admin/orders/3/status/update`
    
    **Description**: Trước đó hóa đơn có mã số `3` đang trong trạng thái *Đang giao* (`statusId` là 2), sau khi cập nhật trạng thái chuyển sang *Đã giao* (`statusId` là 3)
    
    **Response Body:**
    
    ```json
    {
        "orderId": 3,
        "updatedStatusId": 3,
        "updatedStatusName": "Đã giao",
        "canCancel": false
    }
    ```
    
- **Error Response:**
    - Code `404 NOT_FOUND` - Mã hóa đơn không tồn tại
    - Code `400 BAD_REQUEST` - Trạng thái hóa đơn không cho phép cập nhật

## Hủy đơn hàng

---

Hủy đơn hàng chỉ được phép khi đơn hàng phải trong trạng thái *Chờ duyệt*. Mọi trạng thái khác sẽ không được phép.

- **URL:** `/:orderId/cancel` - `orderId` là mã hóa đơn cần hủy
- **Method:** `PUT`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code** `200 OK`
    - **Body:** Tương tự [như trên](Admin%20Order%20API%20a7c55f9677e24b87bc61f82d3bb76628.md)
    
    **Example:** `PUT api/admin/orders/18/cancel`
    
    **Description**: Trước đó hóa đơn có mã số `3` đang trong trạng thái *Chờ duyệt* (`statusId` là 1), sau khi cập nhật trạng thái chuyển sang *Đã hủy* (`statusId` là 4)
    
    **Body:**
    
    ```json
    {
        "orderId": 18,
        "updatedStatusId": 4,
        "updatedStatusName": "Đã hủy",
        "canCancel": false
    }
    ```
    
- **Error Response:**
    - Code `404 NOT_FOUND` - Mã hóa đơn không tồn tại
    - Code `400 BAD_REQUEST` - Trạng thái hóa đơn không cho phép hủy