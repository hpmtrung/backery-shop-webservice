# Authenticate API

Enpoint URL prefix: `/api`

## Table of Contents
  - [Đăng nhập](#đăng-nhập)
  - [Lấy session lần đăng nhập mới nhất](#lấy-session-lần-đăng-nhập-mới-nhất)
  - [Đăng ký](#đăng-ký)
  - [Kích hoạt tài khoản](#kích-hoạt-tài-khoản)
  - [Đặt lại mật khẩu (bước 1)](#đặt-lại-mật-khẩu-bước-1)
  - [Đặt lại mật khẩu (bước 2)](#đặt-lại-mật-khẩu-bước-2)

## Đăng nhập

---

Đăng nhập với thông tin gồm email và mật khẩu

- **URL:** `/api/authenticate`
- **Method:** `POST`
- **Body:** Chứa thông tin đăng nhập gồm
    
    
    | Field | Type | Description | Constraint |
    | --- | --- | --- | --- |
    | email | String | Email dùng đăng nhập | Không trống (NotNull), định dạng email (Email) |
    | password | String | Mật khẩu | Không trống (NotBlank), độ dài tối thiểu 4 kí tự và tối đa 100 kí tự (Size) |
    | rememberMe | boolean | Biến true/false dùng lưu đăng nhập trong 1 tháng |  |
- **Success Response:**
    - **Code:** `200 OK`
    - **Body:** Mã token lưu tại client dùng để xác thực
    
    **Example:** `POST /api/authenticate`
    
    **Request Body:**
    
    ```json
    {
        "email": "user@gmail.com",
        "password": "123456",
        "remember-me": true
    }
    ```
    
    **Response Body:**
    
    ```json
    {
        "id_token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQGdtYWlsLmNvbSIsImF1dGgiOiJST0xFX1VTRVIiLCJleHAiOjE2NTExNDYzNTd9.3dwCm9gvzx_l6MowHAOZotSpR4Ef73F4hJzMIzv51TTSJRZ661AMaxugs8cPgADVaLcuV8CeoismsG6Gx_5Oog"
    }
    ```
    
- Error Response:
    - Code `401 UNAUTHORIZED` - Email hoặc mật khẩu chưa đúng
    

## Lấy session lần đăng nhập mới nhất

---

- **URL:** `/authenticate`
- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code** `200 OK`
    - **Body:** Email của user

## Đăng ký

---

- **URL:** `/register`
- **Method:** `POST`
- **Body:**
    
    
    | Field | Type | Description | Constraint |
    | --- | --- | --- | --- |
    | firstName | String | Tên lót | Không trống (NotBlank), độ dài không quá 50 kí tự (Size) |
    | lastName | String | Tên họ | Không trống (NotBlank), độ dài không quá 50 kí tự (Size) |
    | email | String | Email | Không trống (NotNull), định dạng email (Email) |
    | phone | String | SĐT | Không trống (NotNull), đúng 10 chữ số (Pattern) |
    | address | String | Địa chỉ |  |
    | password | String | Mật khẩu | Không trống (NotBlank), độ dài tối thiểu 4 kí tự (Min) và tối đa 100 kí tự (Max) |
- **Success Response:**
    - **Code** `OK 200`
    
    **Example:** `POST /api/register`
    
    **Request Body:**
    
    ```json
    {
        "firstName": "Laala",
        "lastName": "dfkd",
        "email": "none@cmail.vn",
        "phone": "0125412142",
        "address": "123 aacc",
        "password": "hackx"
    }
    ```
    
- **Error Response:**
    - Code `400 BAD_REQUEST` - Email đã tồn tại

## Kích hoạt tài khoản

---

- **URL:** `/activate`
- **Method:** `GET`
- **Request Params:**
    
    
    | Name | Type | Description |
    | --- | --- | --- |
    | key | String | Token dùng để activate tài khoản |
- **Success Response:**
    - **Code** `OK 200`
    
    **Example:** `POST /api/activate?key=INL8T1GzVJ35tmPCyjhP`
    
- **Error Response:**
    - Code `404 NOT_FOUND` - Token không tồn tại hoặc đã hết hạn
    

## Đặt lại mật khẩu (bước 1)

---

Trước khi đặt lại mật khẩu, user cần nhập vào địa chỉ email họ dùng để đăng ký trước đó. Token được gửi theo email đã cung cấp nếu email tồn tại.

- **URL:** `/reset-password/init`
- **Method:** `POST`
- **Body:** Một chuỗi là email, `Content-Type`: `Text`
- **Success Response:**
    - **Code** `OK 200`
    
    **Example:** `POST /api/reset-password/init`
    
    **Request Body:**
    
    ```json
    user@gmail.com
    ```
    

## Đặt lại mật khẩu (bước 2)

---

Bước này tiến hành khi user chọn click URL được cung cấp trong email thực hiện ở [bước 1](Authenticate%20API%208d466ad860584734982a28a8576addad.md) 

- **URL:** `/reset-password/finish`
- **Method:** `POST`
- **Body:** Thông tin gồm khóa và mật khẩu mới, như sau:
    
    
    | Field | Type | Description | Constraint |
    | --- | --- | --- | --- |
    | key | String | Token dùng để reset mật khẩu | Không trống (NotBlank) |
    | newPassword | String | Mật khẩu mới | Không trống (NotBlank), độ dài tối thiểu 4 và tối đa 100 (Size) |
- **Success Response:**
    - **Code** `OK 200`
    
    **Example:** `POST /api/reset-password/finish`
    
    **Request Body:**
    
    ```json
    {
    	"key": "TLu5yZ7I3Xh2STj70piy",
    	"newPassword": "hackme?"
    }
    ```
    
- **Error Response:**
    - Code `404 NOT_FOUND` - Reset key không tồn tại