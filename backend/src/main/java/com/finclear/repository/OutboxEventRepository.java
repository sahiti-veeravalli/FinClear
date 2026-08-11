package com.finclear.repository;
import com.finclear.domain.OutboxEvent; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface OutboxEventRepository extends JpaRepository<OutboxEvent,UUID>{  }
