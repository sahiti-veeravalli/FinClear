package com.finclear.domain;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="accounts") @Getter @Setter @NoArgsConstructor
public class Account {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) @JoinColumn(name="user_id") private User user;
 @Column(nullable=false,unique=true,length=40) private String accountNumber;
 @Column(nullable=false,precision=19,scale=4) private BigDecimal availableBalance=BigDecimal.ZERO;
 @Column(nullable=false,length=20) private String currency="INR";
 @Column(nullable=false,length=20) private String status="ACTIVE";
 @Version private long version;
 @Column(nullable=false) private Instant createdAt=Instant.now();
}
