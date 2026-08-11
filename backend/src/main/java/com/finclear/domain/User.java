package com.finclear.domain;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;
@Entity @Table(name="users") @Getter @Setter @NoArgsConstructor
public class User {
 @Id @GeneratedValue(strategy=GenerationType.UUID) private UUID id;
 @Column(nullable=false,unique=true,length=190) private String email;
 @Column(nullable=false) private String passwordHash;
 @Column(nullable=false,length=30) private String role="CUSTOMER";
 @Column(nullable=false) private boolean enabled=true;
 @Column(nullable=false) private Instant createdAt=Instant.now();
 public User(String email,String passwordHash,String role){this.email=email;this.passwordHash=passwordHash;this.role=role;}
}
