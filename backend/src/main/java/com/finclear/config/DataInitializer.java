package com.finclear.config;
import com.finclear.domain.*; import com.finclear.repository.*; import org.springframework.boot.CommandLineRunner; import org.springframework.context.annotation.*; import org.springframework.security.crypto.password.PasswordEncoder; import java.math.*; import java.util.*;
@Configuration public class DataInitializer{
 @Bean CommandLineRunner seed(UserRepository users,AccountRepository accounts,PasswordEncoder enc){
  return args->{ if(users.findByEmail("admin@finclear.local").isEmpty()){var u=users.save(new User("admin@finclear.local",enc.encode("Admin@12345"),"ADMIN"));var a=new Account();a.setUser(u);a.setAccountNumber("FC-100001");a.setAvailableBalance(new BigDecimal("1842680.00"));accounts.save(a);}
  if(users.findByEmail("operator@finclear.local").isEmpty()){var u=users.save(new User("operator@finclear.local",enc.encode("Operator@12345"),"OPERATIONS"));var a=new Account();a.setUser(u);a.setAccountNumber("FC-100002");a.setAvailableBalance(new BigDecimal("824920.00"));accounts.save(a);}
  };
 }
}
