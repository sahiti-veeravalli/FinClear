package com.finclear.config;
import com.finclear.domain.*; import com.finclear.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder; import java.math.*; import java.util.*;
@Configuration public class DataInitializer{
 @Bean CommandLineRunner seed(UserRepository users,AccountRepository accounts,ExceptionCaseRepository exceptions,PasswordEncoder enc){
  return args->{ if(users.findByEmail("admin@finclear.local").isEmpty()){var u=users.save(new User("admin@finclear.local",enc.encode("Admin@12345"),"ADMIN"));var a=new Account();a.setUser(u);a.setAccountNumber("FC-100001");a.setAvailableBalance(new BigDecimal("1842680.00"));accounts.save(a);}
  if(users.findByEmail("operator@finclear.local").isEmpty()){var u=users.save(new User("operator@finclear.local",enc.encode("Operator@12345"),"OPERATIONS"));var a=new Account();a.setUser(u);a.setAccountNumber("FC-100002");a.setAvailableBalance(new BigDecimal("824920.00"));accounts.save(a);}
  if(exceptions.count()==0){exceptions.saveAll(java.util.List.of(new ExceptionCase("Gateway settlement missing for captured payments","SETTLEMENT","HIGH",new BigDecimal("482000.00"),"Nisha · Payments Ops","17 payments were captured successfully; 14 await the next T+1 file and 3 have no matching gateway settlement reference."),new ExceptionCase("Beneficiary verification needs review","VENDOR","MEDIUM",new BigDecimal("128500.00"),"Arjun · Risk","Two vendor payout requests changed bank details after approval. Maker-checker confirmation is required."),new ExceptionCase("Refund posted after settlement cutoff","RECONCILIATION","LOW",new BigDecimal("18750.00"),"Unassigned","One refund is correctly ledgered but will appear in tomorrow's settlement reconciliation window.")));}
  };
 }
}
