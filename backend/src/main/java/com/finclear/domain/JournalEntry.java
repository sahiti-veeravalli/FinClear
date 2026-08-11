package com.finclear.domain;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="journal_entries") @Getter @Setter @NoArgsConstructor
public class JournalEntry {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(nullable=false,unique=true,length=80) private String reference;
 @Column(nullable=false,precision=19,scale=4) private BigDecimal debitTotal;
 @Column(nullable=false,precision=19,scale=4) private BigDecimal creditTotal;
 @Column(nullable=false,length=20) private String status="POSTED";
 @Column(nullable=false) private Instant createdAt=Instant.now();
 public JournalEntry(String ref,BigDecimal debit,BigDecimal credit){reference=ref;debitTotal=debit;creditTotal=credit;}
}
