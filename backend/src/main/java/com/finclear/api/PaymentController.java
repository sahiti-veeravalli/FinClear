package com.finclear.api;
import com.finclear.service.PaymentService; import jakarta.validation.constraints.*; import org.springframework.http.*; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.math.*; import java.util.*;
@RestController @RequestMapping("/api/v1/payments") public class PaymentController{
 record Create(@NotNull UUID accountId,@NotBlank String merchant,@NotNull @Positive BigDecimal amount,String currency){}
 private final PaymentService service; public PaymentController(PaymentService s){service=s;}
 @PostMapping public ResponseEntity<?> create(@RequestHeader("Idempotency-Key") String key,@RequestBody Create r,Authentication auth){var p=service.create(new PaymentService.PaymentRequest(r.accountId(),r.merchant(),r.amount(),r.currency()),key,auth.getName());return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("id",p.getId(),"merchant",p.getMerchant(),"amount",p.getAmount(),"currency",p.getCurrency(),"status",p.getStatus(),"createdAt",p.getCreatedAt()));}
 @GetMapping public List<?> latest(){return service.latest().stream().map(p->Map.of("id",p.getId(),"merchant",p.getMerchant(),"amount",p.getAmount(),"currency",p.getCurrency(),"status",p.getStatus(),"createdAt",p.getCreatedAt())).toList();}
}
