# Admin Report API

Đây là các endpoint cung cấp report hỗ trợ Admin UI

Endpoint URL prefix: `/api/admin`

## Table of Contents

  - [Lấy thông tin thống kê hiện lên màn hình Dashboard](#lấy-thông-tin-thống-kê-hiện-lên-màn-hình-dashboard)

## Lấy thông tin thống kê hiện lên màn hình Dashboard

---

Nhận thông tin thống kê về đơn hàng, sản phẩm, ...

- **URL:** `/home`
- **Method:** `GET`
- **Header:** `Authorization: Bearer <token>`
- **Success Response:**
    - **Code:** `200 OK`
    - **Body**: thông tin report
    
    **Example:** `GET /api/admin/home`
    
    ```json
    {
        "totalOrdersNum": 31, // tổng số đơn hàng
        "todayOrdersNum": 0, // số đơn hàng trong ngày
        "todayProcessingOrdersNum": 0, // số đơn hàng đang chờ duyệt trong ngày
        "todayCancelOrdersNum": 0, // số đơn hàng đã hủy trong ngày
        "todayDispatchOrdersNum": 0, // số đơn hàng đang giao trong ngày
        "todayShippedOrdersNum": 0, // số đơn hàng đã giao trong ngày
        "totalAvailableProductVariantsNum": 74, // số sản phẩm đang bày bán
        "totalSoldProductVariantsNum": 2286, // số sản phẩm đã bán
        "todaySoldProductVariantsNum": 0, // số sản phẩm đã bán trong ngày
        "topRecentOrders": [ // danh sách 5 đơn hàng mới nhất
            {
                "orderId": 3019,
                "userImageUrl": "6.jpg",
                "userFullName": "Users Websd",
                "createdDate": "2022-04-21T15:46:16Z",
                "statusId": 1,
                "statusName": "Chờ duyệt",
                "orderTotal": 700000,
                "paidByCash": true,
                "note": ""
            },
            {
                "orderId": 2027,
                "userImageUrl": "6.jpg",
                "userFullName": "Users Websd",
                "createdDate": "2022-04-21T08:38:06Z",
                "statusId": 1,
                "statusName": "Chờ duyệt",
                "orderTotal": 60000,
                "paidByCash": true,
                "note": ""
            },
            {
                "orderId": 2026,
                "userImageUrl": "6.jpg",
                "userFullName": "Users Websd",
                "createdDate": "2022-04-21T08:12:20Z",
                "statusId": 1,
                "statusName": "Chờ duyệt",
                "orderTotal": 260000,
                "paidByCash": true,
                "note": ""
            },
            {
                "orderId": 2024,
                "userImageUrl": "6.jpg",
                "userFullName": "Users Websd",
                "createdDate": "2022-04-19T17:30:30Z",
                "statusId": 1,
                "statusName": "Chờ duyệt",
                "orderTotal": 350000,
                "paidByCash": true,
                "note": "safsf"
            },
            {
                "orderId": 2023,
                "userImageUrl": "6.jpg",
                "userFullName": "Users Websd",
                "createdDate": "2022-04-19T17:29:47Z",
                "statusId": 1,
                "statusName": "Chờ duyệt",
                "orderTotal": 960000,
                "paidByCash": true,
                "note": ""
            }
        ]
    }
    ```