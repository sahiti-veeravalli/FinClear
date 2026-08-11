package com.finclear.domain;
import jakarta.persistence.*; import lombok.*; import java.time.Instant; import java.util.UUID;
@Entity @Table(name="outbox_events") @Getter @Setter @NoArgsConstructor
public class OutboxEvent { @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id; @Column(nullable=false,length=100) private String eventType; @Column(nullable=false,columnDefinition="TEXT") private String payload; @Column(nullable=false) private Instant createdAt=Instant.now(); @Column(nullable=false) private boolean published=false;
 public OutboxEvent(String type,String payload){this.eventType=type;this.payload=payload;}
}
