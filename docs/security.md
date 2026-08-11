# Security

JWT bearer authentication is used for the API. Passwords are BCrypt hashed. Role claims are mapped to Spring Security authorities.

Roles:
- CUSTOMER
- OPERATIONS
- ADMIN

Production deployment must replace the development JWT secret and credentials with managed secrets.
