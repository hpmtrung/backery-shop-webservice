# User Account API

Endpoint URL Prefix: `api/account`

## Table of Contents

  - [Lấy thông tin cá nhân](#lấy-thông-tin-cá-nhân)
  - [Cập nhật thông tin cá nhân](#cập-nhật-thông-tin-cá-nhân)
  - [Thay đổi ảnh cá nhân](#thay-đổi-ảnh-cá-nhân)
  - [Thay đổi mật khẩu](#thay-đổi-mật-khẩu)


## Lấy thông tin cá nhân

- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code** `200 OK`
    - **Body:** Thông tin cá nhân user login
    
    **Example:** `GET /api/account`
    
    **Response Body:**
    
    ```json
    {
        "firstName": "Websd",
        "lastName": "Users",
        "email": "user@gmail.com",
        "phone": "0323456784",
        "imageUrl": "http://localhost:8080/api/public/image/user/6.jpg",
        "address": "New Address 2ssdf",
        "langKey": "en",
        "activated": true,
        "authorities": [
            "ROLE_USER"
        ]
    }
    ```
    

## Cập nhật thông tin cá nhân

---

- **URL:** `/info`
- **Method:** `PUT`
- **Header:** `Authorization: Bearer <token>`
- **Body:** Thông tin cá nhân user muốn cập nhật, có dạng:
    
    
    | Field | Type | Description | Constraint |
    | --- | --- | --- | --- |
    | firstName | String | Tên lót | Không trống (NotBlank), độ dài không quá 50 kí tự (Size) |
    | lastName | String | Tên họ | Không trống (NotBlank), độ dài không quá 50 kí tự (Size) |
    | phone | String | SĐT | Không trống (NotNull), đúng 10 chữ số (Pattern) |
    | address | String | Địa chỉ |  |
    | langKey | String | Mã ngôn ngữ | Không trống (NotBlank) |
- **Success Response:**
    - **Code** `200 OK`
    
    **Example:** `PUT /api/account/info`
   

## Thay đổi ảnh cá nhân

---

- **URL:** `/image`
- **Method:** `PUT`
- **Header:** `Authorization: Bearer <token>`
- **Request Params:** Content-Type: `multipart/form-data`
    
    
    | Name | Type | Description | Contraint |
    | --- | --- | --- | --- |
    | image | file | File ảnh avatar | Size tối đa 10MB |
- **Success Response:**
    - **Code** `200 OK`
    
    **Example:** `PUT /api/account/image`
    

## Thay đổi mật khẩu

---

- **URL:** `/change-password`
- **Method:** `POST`
- **Header:** `Authorization: Bearer <token>`
- **Body:** Thông tin gồm mật khẩu user nhập và mật khẩu mới
    
    
    | Field | Type | Description | Contraint |
    | --- | --- | --- | --- |
    | currentPassword | String | Mật khẩu user dùng để kiểm tra | Không trống (NotBlank), độ dài tối thiểu 4 kí tự và tối đa 100 kí tự (Size) |
    | newPassword | String | Mật khẩu mới | Không trống (NotBlank), độ dài tối thiểu 4 kí tự và tối đa 100 kí tự (Size) |
- **Success Response:**
    - **Code** `200 OK`
    
    **Example:** `POST /api/account/change-password`
    
    **Request Body:**
    
    ```json
    {
    	"currentPassword": "usernotfound",
    	"newPassword": "userfound",
    }
    ```
    
- **Error Response:**
    - Code `400 BAD_REQUEST` - Mật khẩu nhập vào (`currentPassword`) không đúng
