package com.finclear.api;
import com.finclear.domain.Account; import com.finclear.repository.AccountRepository; import org.springframework.security.core.Authentication; import org.springframework.web.bind.annotation.*; import java.util.*;
@RestController @RequestMapping("/api/v1/accounts") public class AccountController{
 private final AccountRepository repo; public AccountController(AccountRepository r){repo=r;}
 @GetMapping public List<Map<String,Object>> all(Authentication a){return repo.findAll().stream().map(x->Map.<String,Object>of("id",x.getId(),"accountNumber",x.getAccountNumber(),"balance",x.getAvailableBalance(),"currency",x.getCurrency(),"status",x.getStatus())).toList();}
}
