package com.finclear.repository;
import com.finclear.domain.ExceptionCase; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface ExceptionCaseRepository extends JpaRepository<ExceptionCase,UUID>{ List<ExceptionCase> findAllByOrderByCreatedAtDesc(); }
