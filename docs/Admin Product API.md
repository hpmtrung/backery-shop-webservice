# Admin Product API

Endpoint URL prefix: `/api/admin/products`

## Table of Contents

  - [Lấy danh sách thông tin cơ bản các sản phẩm](#lấy-danh-sách-thông-tin-cơ-bản-các-sản-phẩm)
  - [Lấy thông tin chi tiết của sản phẩm](#lấy-thông-tin-chi-tiết-của-sản-phẩm)
  - [Lấy danh sách biến thể của sản phẩm](#lấy-danh-sách-biến-thể-của-sản-phẩm)
  - [Tạo sản phẩm mới](#tạo-sản-phẩm-mới)
  - [Thay đổi thông tin sản phẩm](#thay-đổi-thông-tin-sản-phẩm)
  - [Lấy danh sách hình ảnh của sản phẩm](#lấy-danh-sách-hình-ảnh-của-sản-phẩm)
  - [Thêm hình ảnh cho một sản phẩm](#thêm-hình-ảnh-cho-một-sản-phẩm)
  - [Xóa hình ảnh của một sản phẩm](#xóa-hình-ảnh-của-một-sản-phẩm)

## Lấy danh sách thông tin cơ bản các sản phẩm

---

Trả về 1 danh sách thông tin cơ bản các sản phẩm (mặc định sắp theo tăng dần mã sản phẩm)

- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Request Params:**
    
    
    | Name | Type | Description | Default |
    | --- | --- | --- | --- |
    | page | int | Số thứ tự trang | 0 |
    | size | int | Số lượng sản phẩm muốn lấy | 10 |
- **Success Response:**
    - `200 OK`
    
    **Example:** `GET /api/admin/products?page=1&size=2`
    
    **Response Body**:
    
    ```json
    [
        {
            "id": 3,
            "name": "Bánh Cupcake Kiểu Nhật Tua",
            "categoryName": "Pudding Chuối",
            "ingredients": "Bột ca cao, Bột bánh, Baking Soda, Muối, Bơ, Trứng, Sữa, Kem chua, Chiết xuất Vanilla, Chất tạo màu thực phẩm, Bột mì, Dầu Canola, Bột ca cao, Bột nở, Hạt Sô-cô-la 65%, Đường bánh kẹo, Bông đường, Lòng trắng trứng"
        },
        {
            "id": 4,
            "name": "Bánh Cupcake Cổ điển hương Sô-cô-la",
            "categoryName": "Cheesecake",
            "ingredients": "Bột mì, Đường cát, Dầu Canola, Bột ca cao, Bột nở, Baking Soda, Muối, Sữa, Trứng, Chiết xuất Vanilla, Hạt Sô-cô-la 65%, Đường bánh kẹo, Bông đường"
        }
    ]
    ```
    

## Lấy thông tin chi tiết của sản phẩm

---

Trả về thông tin chi tiết của một sản phẩm theo mã sản phẩm (Không bao gồm danh sách các biến thể của sản phẩm)

(**Lưu ý**: Xem endpoint lấy danh sách biến thể của sản phẩm bên dưới)

- **URL:** `/:productId` - `productId` là mã sản phẩm
- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code** `200 OK`
    
    **Example:** `GET /api/admin/products/3`
    
    **Body:**
    
    ```json
    {
        "id": 3,
        "name": "Bánh Cupcake Kiểu Nhật Tua",
        "categoryId": 5,
        "ingredients": "Bột ca cao, Bột bánh, Baking Soda, Muối, Bơ, Trứng, Sữa, Kem chua, Chiết xuất Vanilla, Chất tạo màu thực phẩm, Bột mì, Dầu Canola, Bột ca cao, Bột nở, Hạt Sô-cô-la 65%, Đường bánh kẹo, Bông đường, Lòng trắng trứng",
        "allergens": "",
        "available": false
    }
    ```
    
- **Error Response:**
    - Code `404 NOT_FOUND` - Mã sản phẩm không tồn tại

## Lấy danh sách biến thể của sản phẩm

---

Trả về danh sách biến thể của sản phẩm theo mã sản phẩm.

- **URL:** `/variants/:productId` - `productId` là mã sản phẩm cần lấy các biến thể
- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code** `200 OK`
    
    **Example:** `GET api/admin/products/variants/2`
    
    **Response body:**
    
    ```json
    [
        {
            "id": 2093,
            "productId": 2,
            "typeId": 3,
            "cost": 10000,
            "price": 20000,
            "hot": false,
            "available": true
        },
        {
            "id": 2094,
            "productId": 2,
            "typeId": 2,
            "cost": 30000,
            "price": 40000,
            "hot": false,
            "available": false
        }
    ]
    ```
    
- **Error Response:**
    - Code `400 BAD_REQUEST`- Mã sản phẩm không tồn tại

## Tạo sản phẩm mới

---

Tạo sản phẩm mới và trả về mã số sản phẩm vừa tạo.

- **Method:** `POST`
- **Header:** `Authorization: Bearer <token>`
- **Body:** Thông tin sản phẩm cần lưu có dạng như sau:
    
    
    | Field | Type | Description | Constraint |
    | --- | --- | --- | --- |
    | name | String | Tên sản phẩm | Không trống (NotBlank) |
    | categoryId | Integer | Mã thể loại | Không trống (NotNull) |
    | ingredients | String | Nguyên liệu |  |
    | allergens | String | Dị ứng |  |
    | available | boolean | Tình trạng còn hàng |  |
- **Success Response:**
    - **Code** `201 CREATED`
    - **Body:** mã số sản phẩm mới
    
    **Example:** `POST api/admin/products`
    
    **Request Body:**
    
    ```json
    {
        "name": "Bánh hành",
        "ingredients": "Muối, đường",
        "available": true,
        "categoryId": 3,
        "allergens": "Không"
    }
    ```
    
    **Response Body:**
    
    ```json
    3044
    ```
    
- **Error Response:**
    - Code `400 BAD_REQUEST` - Tên sản phẩm đã tồn tại
    - Code `404 NOT_FOUND` - Mã thể loại không tồn tại

## Thay đổi thông tin sản phẩm

---

- **URL:** `/info`
- **Method:** `PUT`
- **Header:** `Authorization: Bearer <token>`
- **Body:** Thông tin sản phẩm cần cập nhật (như trên) và `id` dùng để xác định sản phẩm
    
    ```json
    {
    	"id": "", // mã sản phẩm cần cập nhật
    	"name": "",
    	"categoryId": "",
    	"ingredients": "",
    	"allergens": "",
    	"available": true,
    }
    ```
    
- **Success Response:**
    - **Code** `200 OK`
    
    **Example:** `PUT /api/admin/products/3/info/`
    
    **Request Body:**
    
    ```json
    {
        "name": "Bánh Cupcake Kiểu Nhật",
        "ingredients": "Bột ca cao, Bột bánh, Baking Soda, Muối, Bơ, Trứng, Sữa, Kem chua, Chiết xuất Vanilla, Chất tạo màu thực phẩm, Bột mì, Dầu Canola, Bột ca cao, Bột nở, Hạt Sô-cô-la 65%, Đường bánh kẹo, Bông đường, Lòng trắng trứng",
        "available": false,
        "categoryId": 1
    }
    ```
    
- **Error Response:**
    - Code `404 NOT_FOUND`, Entity: `product`, Error Key: `notFoundId` - Mã sản phẩm cập nhật không tồn tại
    - Code `404 NOT_FOUND`, Entity: `category`, Error Key: `notFoundId` - Mã thể loại không tồn tại
    - Code `400 BAD_REQUEST` - Tên sản phẩm đã tồn tại
    

## Lấy danh sách hình ảnh của sản phẩm

---

- **URL:** `/:productId/images/` - `productId` là mã sản phẩm cần lấy ảnh
- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code** `200 OK`
    - **Body: D**anh sách hình ảnh
    
    **Example:** `GET /api/admin/products/1/images`
    
    **Response Body:**
    
    ```json
    [
        {
            "id": 1,
            "imagePath": "http://localhost:8080/api/public/image/product/1637281686544.jpg"
        },
        {
            "id": 2,
            "imagePath": "http://localhost:8080/api/public/image/product/1637281694203.jpg"
        },
        {
            "id": 2077,
            "imagePath": "http://localhost:8080/api/public/image/product/1650899338119.png"
        }
    ]
    ```
    
- **Error Response:**
    - Code `404 NOT_FOUND` - Mã sản phẩm không tồn tại
    

## Thêm hình ảnh cho một sản phẩm

---

- **URL:** `/:productId/images` - `productId` là mã sản phẩm cần lấy ảnh
- **Method:** `POST`
- **Header:** `Authorization: Bearer <token>`
- **Request:**
    - **Body:** `multipart/form-data` chứa một danh sách hình ảnh cần thêm, param key là `images`
- **Success Response:**
    - **Code** `201 CREATED`

## Xóa hình ảnh của một sản phẩm

---

- **URL:** `/:productId/images` - `productId` là mã sản phẩm cần lấy ảnh
- **Method:** `DELETE`
- **Header:** `Authorization: Bearer <token>`
- **Request:**
    - **Body:** Chứa thông tin một mảng mã ảnh cần xóa
    
    | Field | Type | Description | Constraint |
    | --- | --- | --- | --- |
    | deletesImageIds | int[] | Mảng chứa mã ảnh cần xóa | Không trống (NotNull), mảng có độ dài ít nhất 1 (Min) và tối đa 10 (Max) |
- **Success Response:**
    - **Code** `200 OK`
- Error Response:
    - Code `404 NOT_FOUND` - Có ít nhất 1 mã ảnh không tồn tại
    - Code `400 BAD_REQUEST` - Có ít nhất 1 ảnh không thuộc sản phẩm