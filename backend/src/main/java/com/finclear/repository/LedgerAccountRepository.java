package com.finclear.repository;
import com.finclear.domain.LedgerAccount; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface LedgerAccountRepository extends JpaRepository<LedgerAccount,UUID>{  }
