package com.finclear.repository;
import com.finclear.domain.Account; import jakarta.persistence.LockModeType; import org.springframework.data.jpa.repository.*; import org.springframework.data.repository.query.Param; import java.util.*; import java.util.concurrent.*;
public interface AccountRepository extends JpaRepository<Account,UUID>{
 Optional<Account> findByAccountNumber(String accountNumber);
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select a from Account a where a.id=:id") Optional<Account> findByIdForUpdate(@Param("id") UUID id);
}
