package com.finclear.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="audit_logs") @Getter @Setter @NoArgsConstructor
public class AuditLog {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(length=190) private String actor;
 @Column(nullable=false,length=80) private String action;
 @Column(length=80) private String entityType;
 @Column(length=80) private String entityId;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 @Column(columnDefinition="TEXT") private String metadata;
 public AuditLog(String actor,String action,String entityType,String entityId,String metadata){this.actor=actor;this.action=action;this.entityType=entityType;this.entityId=entityId;this.metadata=metadata;}
}
