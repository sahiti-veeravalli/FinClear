package com.finclear.domain;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity @Table(name="exception_cases") @Getter @Setter @NoArgsConstructor
public class ExceptionCase {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(nullable=false,length=160) private String title;
 @Column(nullable=false,length=40) private String category;
 @Column(nullable=false,length=20) private String severity;
 @Column(nullable=false,precision=19,scale=4) private BigDecimal amount;
 @Column(nullable=false,length=3) private String currency="INR";
 @Column(nullable=false,length=20) private String status="OPEN";
 @Column(nullable=false,length=190) private String owner="Unassigned";
 @Column(nullable=false,length=700) private String evidence;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 @Column(nullable=false) private Instant updatedAt=Instant.now();
 public ExceptionCase(String title,String category,String severity,BigDecimal amount,String owner,String evidence){this.title=title;this.category=category;this.severity=severity;this.amount=amount;this.owner=owner;this.evidence=evidence;}
}
