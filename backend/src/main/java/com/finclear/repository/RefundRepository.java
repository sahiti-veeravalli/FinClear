package com.finclear.repository;
import com.finclear.domain.Refund; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface RefundRepository extends JpaRepository<Refund,UUID>{  }
