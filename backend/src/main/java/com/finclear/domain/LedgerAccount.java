package com.finclear.domain;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.util.UUID;
@Entity @Table(name="ledger_accounts") @Getter @Setter @NoArgsConstructor
public class LedgerAccount {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(nullable=false,unique=true,length=80) private String code;
 @Column(nullable=false,length=120) private String name;
 @Column(nullable=false,precision=19,scale=4) private BigDecimal balance=BigDecimal.ZERO;
}
