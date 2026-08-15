package com.finclear.api;
import org.springframework.http.*; import org.springframework.security.authentication.BadCredentialsException; import org.springframework.web.bind.MethodArgumentNotValidException; import org.springframework.web.bind.MissingRequestHeaderException; import org.springframework.web.bind.annotation.*; import java.time.Instant; import java.util.*;
@RestControllerAdvice public class ApiExceptionHandler{
 private Map<String,Object> body(String error){return Map.of("timestamp",Instant.now(),"error",error);}
 @ExceptionHandler({IllegalArgumentException.class,IllegalStateException.class}) ResponseEntity<?> bad(RuntimeException e){return ResponseEntity.badRequest().body(Map.of("timestamp",Instant.now(),"error",e.getMessage()));}
 @ExceptionHandler({MethodArgumentNotValidException.class,MissingRequestHeaderException.class}) ResponseEntity<?> validation(Exception e){return ResponseEntity.badRequest().body(body("Invalid request"));}
 @ExceptionHandler(BadCredentialsException.class) ResponseEntity<?> unauthorized(BadCredentialsException e){return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body("Invalid credentials"));}
 @ExceptionHandler(Exception.class) ResponseEntity<?> generic(Exception e){return ResponseEntity.status(500).body(body("Internal server error"));}
}
