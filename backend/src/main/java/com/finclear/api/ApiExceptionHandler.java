package com.finclear.api;
import org.springframework.http.*; import org.springframework.web.bind.annotation.*; import java.time.Instant; import java.util.*;
@RestControllerAdvice public class ApiExceptionHandler{
 @ExceptionHandler({IllegalArgumentException.class,IllegalStateException.class}) ResponseEntity<?> bad(RuntimeException e){return ResponseEntity.badRequest().body(Map.of("timestamp",Instant.now(),"error",e.getMessage()));}
 @ExceptionHandler(Exception.class) ResponseEntity<?> generic(Exception e){return ResponseEntity.status(500).body(Map.of("timestamp",Instant.now(),"error","Internal server error"));}
}
