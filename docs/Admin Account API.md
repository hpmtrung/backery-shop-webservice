# Admin Account API

Endpoint URL prefix: `/api/admin/accounts`

## Table of Contents

  - [Lấy danh sách các tài khoản](#lấy-danh-sách-các-tài-khoản)
  - [Lấy thông tin tài khoản theo mã tài khoản](#lấy-thông-tin-tài-khoản-theo-mã-tài-khoản)
  - [Tạo tài khoản mới](#tạo-tài-khoản-mới)
  - [Cập nhật tài khoản](#cập-nhật-tài-khoản)


## Lấy danh sách các tài khoản

---

Trả về 1 danh sách các tài khoản (mặc định sắp theo tăng dần mã tài khoản)

- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Request Params:**
    
    
    | Name | Type | Description | Default |
    | --- | --- | --- | --- |
    | page | int | Số thứ tự trang | 0 |
    | size | int | Số lượng tài khoản muốn lấy | 10 |
- **Success Response:**
    - **Code:** `200 OK`
    - **Body:** Danh sách tài khoản
    
    **Example:** `GET /api/admin/accounts/?page=0&size=2`
    
    ```json
    [
        {
            "id": 4,
            "firstName": "Nam",
            "lastName": "Hoàng",
            "email": "nam@gmail.com",
            "phone": "0908765431",
            "imageUrl": "http://localhost:8080/api/public/image/user/default.png",
            "address": "test2",
            "activated": false,
            "langKey": "en",
            "authorities": [
                "ROLE_USER"
            ]
        },
        {
            "id": 6,
            "firstName": "Websd",
            "lastName": "Users",
            "email": "user@gmail.com",
            "phone": "0323456784",
            "imageUrl": "http://localhost:8080/api/public/image/user/6.jpg",
            "address": "New Address 2ssdf",
            "activated": true,
            "langKey": "en",
            "authorities": [
                "ROLE_USER"
            ]
        }
    ]
    ```
    

## Lấy thông tin tài khoản theo mã tài khoản

---

Trả về thông tin một tài khoản xác định theo mã tài khoản

- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code:** `200 OK`
    - **Body:** Thông tin tài khoản
    
    **Example:** `GET /api/admin/accounts/4`
    
    ```json
    {
        "id": 4,
        "firstName": "Nam",
        "lastName": "Hoàng",
        "email": "nam@gmail.com",
        "phone": "0908765431",
        "imageUrl": "http://localhost:8080/api/public/image/user/default.png",
        "address": "test2",
        "activated": false,
        "langKey": "en",
        "authorities": [
            "ROLE_USER"
        ]
    }
    ```
    
- **Error Response:**
    - Code `404 NOT_FOUND`: Mã tài khoản không tồn tại

## Tạo tài khoản mới

---

Administrator tạo tài khoản mới bao gồm thông tin cơ bản (Profile) và thông tin dùng trong hệ thống (Activated, Role, ...)

- **Method:** `POST`
- **Header:** `Authorization: Bearer <token>`
- **Body:** Chứa thông tin tài khoản cần tạo, lưu ý chỉ nhận các thông tin sau:
    
    
    | Field | Type | Description | Constraint |
    | --- | --- | --- | --- |
    | firstName | String | Tên lót | Không trống (NotBlank), độ dài không quá 50 kí tự (Size) |
    | lastName | String | Tên họ | Không trống (NotBlank), độ dài không quá 50 kí tự (Size) |
    | email | String | Email | Không trống (NotNull), định dạng email (Email) |
    | phone | String | SĐT | Không trống (NotNull), đúng 10 chữ số (Pattern) |
    | address | String | Địa chỉ |  |
    | langKey | String | Mã ngôn ngữ | Không trống (NotBlank) |
    | authorities | String[] | Danh sách quyền | Không trống (NotNull), ít nhất 1 quyền (Size) |
    
    **Example:**
    
    ```json
    {
        "firstName": "Nguyễn",
        "lastName": "Hoàng",
        "email": "alalls@cmail.com",
        "phone": "0908765431",
        "address": "test2",
        "langKey": "en",
        "authorities": ["ROLE_USER"]
    }
    ```
    
- **Success Response:**
    - **Code:** `201 CREATED`
    - **Body:** Thông tin tài khoản đã tạo kèm mã tài khoản
    
    **Example:** `POST /api/admin/accounts`
    
    **Request Body:**
    
    ```json
    {
        "firstName": "Nguyễn",
        "lastName": "Hoàng",
        "email": "alalls@cmail.com",
        "phone": "0908765431",
        "address": "test2",
        "langKey": "en",
        "authorities": ["ROLE_USER"]
    }
    ```
    
    **Response Body:**
    
    ```json
    {
        "id": 4023,
        "firstName": "Nguyễn",
        "lastName": "Hoàng",
        "email": "alalls@cmail.com",
        "phone": "0908765431",
        "imageUrl": "http://localhost:8080/api/public/image/user/default.png",
        "address": "test2",
        "activated": true,
        "langKey": "en",
        "authorities": [
            "ROLE_USER"
        ]
    }
    ```
    
- Error Response:
    - Code `400 BAD_REQUEST` - Email đã sử dụng
        
        ```json
        {
            "entityName": "account",
            "errorKey": "usedEmail",
            "type": "https://localhost:8080/api/public/docs/error/#entity",
            "title": "Email is already used",
            "status": 400
        }
        ```
        

---

## Cập nhật tài khoản

Administrator cập nhật thông tin tài khoản.

- **Method:** `PUT`
- **Header:** `Authorization: Bearer <token>`
- **Body:** Chứa thông tin tài khoản cho phép cập nhật ([như trên](Admin%20Account%20API%2004a723a3061546d8a4a931e96115b714.md)) và `id` dùng xác định tài khoản (Không trống `NotNull`)
    
    **Example:**
    
    ```json
    {
    		"id": "4023",
        "firstName": "Nguyễn",
        "lastName": "Hoàng",
        "email": "alalls@cmail.com",
        "phone": "0908765431",
        "address": "test2",
        "langKey": "vi",
        "authorities": ["ROLE_USER", "ROLE_ADMIN"]
    }
    ```
    
- **Success Response:**
    - **Code:** `200 OK`
    - **Body:** Thông tin tài khoản được cập nhật
    
    **Example:** `PUT /api/admin/accounts/`
    
    **Request Body:**
    
    ```json
    {
    		"id": "4023",
        "firstName": "Nguyễn",
        "lastName": "Hoàng",
        "email": "alalls@cmail.com",
        "phone": "0908765431",
        "address": "test2",
        "langKey": "vi",
        "authorities": ["ROLE_USER", "ROLE_ADMIN"]
    }
    ```
    
    **Response Body:**
    
    ```json
    {
        "id": 4023,
        "firstName": "Nguyễn",
        "lastName": "Hoàng",
        "email": "alalls@cmail.com",
        "phone": "0908765431",
        "imageUrl": "http://localhost:8080/api/public/image/user/default.png",
        "address": "test2",
        "activated": true,
        "langKey": "vi",
        "authorities": [
            "ROLE_USER",
            "ROLE_ADMIN"
        ]
    }
    ```
    
- Error Response:
    - Code `404 NOT_FOUND` - Mã tài khoản không tồn tại