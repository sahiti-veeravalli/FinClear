package com.finclear.security;
import io.jsonwebtoken.*; import io.jsonwebtoken.security.Keys; import org.springframework.beans.factory.annotation.Value; import org.springframework.stereotype.Service; import java.nio.charset.StandardCharsets; import javax.crypto.SecretKey; import java.time.Instant; import java.util.*;
@Service public class JwtService {
 private final SecretKey key; private final long expiry;
 public JwtService(@Value("${finclear.jwt.secret}") String secret,@Value("${finclear.jwt.expiry-minutes}") long expiry){key=Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));this.expiry=expiry;}
 public String create(String subject,String role){return Jwts.builder().subject(subject).claim("role",role).issuedAt(new Date()).expiration(Date.from(Instant.now().plusSeconds(expiry*60))).signWith(key).compact();}
 public Claims parse(String token){return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();}
}
