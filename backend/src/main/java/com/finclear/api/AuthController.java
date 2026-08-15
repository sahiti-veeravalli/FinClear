package com.finclear.api;
import com.finclear.repository.UserRepository; import com.finclear.security.JwtService; import jakarta.validation.Valid; import jakarta.validation.constraints.*; import org.springframework.security.crypto.password.PasswordEncoder; import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/v1/auth") public class AuthController{
 record Login(@Email String email,@NotBlank String password){} record Token(String token,String role){}
 private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthController(UserRepository u,PasswordEncoder e,JwtService j){users=u;encoder=e;jwt=j;}
 @PostMapping("/login") public Token login(@Valid @RequestBody Login r){var u=users.findByEmail(r.email()).orElseThrow(()->new org.springframework.security.authentication.BadCredentialsException("Invalid credentials"));if(!encoder.matches(r.password(),u.getPasswordHash()))throw new org.springframework.security.authentication.BadCredentialsException("Invalid credentials");return new Token(jwt.create(u.getEmail(),u.getRole()),u.getRole());}
}
