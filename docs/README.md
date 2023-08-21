# Cake corner API Docs

[Public API](Public%20API.md)

[Authenticate API](Authenticate%20API.md)

Các enpoint sau cần yêu cầu quyền tối thiều `ROLE_USER`

**Lưu ý:**

- Mọi endpoint thuộc quyền Admin, đều có thông báo lỗi yêu cầu xác thực:
    - Code `401 UNAUTHORIZED` - Chưa đăng nhập
    - Code `403 FORBIDDEN` - Không phải Admin
- Mọi endpoint thuộc quyền user có tài khoản đã kích hoạt, đều có thông báo lỗi yêu cầu xác thực:
    - Code `401 UNAUTHORIZED` - Chưa đăng nhập

### Authenticated User

[User Account API](User%20Account%20API.md)

[User Checkout API](User%20Checkout%20API.md)

[User Order API](User%20Order%20API.md)

### Admin

[Admin Report API](Admin%20Report%20API.md)

[Admin Account API](Admin%20Account%20API.md)

[Admin Order API](Admin%20Order%20API.md)

[Admin Product API](Admin%20Product%20API.md)

[Admin Variant API](Admin%20Variant%20API.md)