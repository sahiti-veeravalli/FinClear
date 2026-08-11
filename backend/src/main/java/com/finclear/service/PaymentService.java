package com.finclear.service;
import com.finclear.domain.*; import com.finclear.repository.*; import org.springframework.stereotype.Service; import org.springframework.transaction.annotation.Transactional; import java.math.*; import java.time.*; import java.util.*;
@Service public class PaymentService{
 private final PaymentRepository payments; private final AccountRepository accounts; private final JournalEntryRepository journals; private final OutboxEventRepository outbox; private final AuditLogRepository audit;
 public PaymentService(PaymentRepository p,AccountRepository a,JournalEntryRepository j,OutboxEventRepository o,AuditLogRepository audit){payments=p;accounts=a;journals=j;outbox=o;this.audit=audit;}
 public record PaymentRequest(UUID accountId,String merchant,BigDecimal amount,String currency){}
 @Transactional public Payment create(PaymentRequest r,String key,String actor){
   if(key==null||key.isBlank()) throw new IllegalArgumentException("Idempotency-Key is required");
   var existing=payments.findByIdempotencyKey(key); if(existing.isPresent()) return existing.get();
   if(r.amount()==null||r.amount().signum()<=0) throw new IllegalArgumentException("Amount must be positive");
   var a=accounts.findByIdForUpdate(r.accountId()).orElseThrow(()->new IllegalArgumentException("Account not found"));
   if(a.getStatus().equals("BLOCKED")) throw new IllegalStateException("Account is blocked");
   if(a.getAvailableBalance().compareTo(r.amount())<0) throw new IllegalStateException("Insufficient funds");
   a.setAvailableBalance(a.getAvailableBalance().subtract(r.amount()));
   var p=new Payment();p.setPayer(a);p.setMerchant(r.merchant());p.setAmount(r.amount());p.setCurrency(r.currency()==null?"INR":r.currency());p.setIdempotencyKey(key);p.setStatus("SUCCEEDED");p.setUpdatedAt(Instant.now());payments.save(p);
   var j=new JournalEntry("PAY-"+key,r.amount(),r.amount());journals.save(j);
   outbox.save(new OutboxEvent("PaymentSucceeded","{\"paymentId\":\""+p.getId()+"\"}"));
   audit.save(new AuditLog(actor,"PAYMENT_CREATED","PAYMENT",p.getId().toString(),"{\"amount\":\""+r.amount()+"\"}"));
   return p;
 }
 @Transactional(readOnly=true) public List<Payment> latest(){return payments.findTop50ByOrderByCreatedAtDesc();}
}
