package com.finclear.repository;
import com.finclear.domain.JournalEntry; import org.springframework.data.jpa.repository.JpaRepository; import java.util.*;
public interface JournalEntryRepository extends JpaRepository<JournalEntry,UUID>{ Optional<JournalEntry> findByReference(String reference); }
