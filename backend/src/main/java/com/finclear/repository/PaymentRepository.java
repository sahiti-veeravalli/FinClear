package com.finclear.repository;
import com.finclear.domain.Payment; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface PaymentRepository extends JpaRepository<Payment,UUID>{Optional<Payment> findByIdempotencyKey(String key); List<Payment> findTop50ByOrderByCreatedAtDesc();}
