package com.finclear.domain;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="refunds") @Getter @Setter @NoArgsConstructor
public class Refund {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private Payment payment;
 @Column(nullable=false,precision=19,scale=4) private BigDecimal amount;
 @Column(nullable=false,length=20) private String status="SUCCEEDED";
 @Column(nullable=false) private Instant createdAt=Instant.now();
}
