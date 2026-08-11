package com.finclear.repository;
import com.finclear.domain.AuditLog; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface AuditLogRepository extends JpaRepository<AuditLog,UUID>{  }
