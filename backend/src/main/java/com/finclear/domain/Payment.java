package com.finclear.domain;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="payments") @Getter @Setter @NoArgsConstructor
public class Payment {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="payer_account_id") private Account payer;
 @Column(nullable=false,length=190) private String merchant;
 @Column(nullable=false,precision=19,scale=4) private BigDecimal amount;
 @Column(nullable=false,length=3) private String currency;
 @Column(nullable=false,length=30) private String status="PROCESSING";
 @Column(nullable=false,unique=true,length=100) private String idempotencyKey;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 @Column(nullable=false) private Instant updatedAt=Instant.now();
}
