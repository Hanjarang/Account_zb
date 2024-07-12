package com.example.account.controller;

import com.example.account.domain.Account;
import com.example.account.dto.AccountDto;
import com.example.account.dto.AccountInfo;
import com.example.account.dto.CreateAccount;
import com.example.account.dto.CreateAccount.Response;
import com.example.account.dto.DeleteAccount;
import com.example.account.service.AccountService;
import com.example.account.service.RedisTestService;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AccountController {
	private final AccountService accountService;
	private final RedisTestService redisTestServive;
	
    @PostMapping("/account")
    public CreateAccount.Response createAccount(
    		@RequestBody @Valid CreateAccount.Request request
    ) {
    	return CreateAccount.Response.from(
    			accountService.createAccount(
    					request.getUserId(),
    					request.getInitialBalance()
    					)
    	);
    }
    
    @DeleteMapping("/account")
    public DeleteAccount.Response deleteAccount(
    		@RequestBody @Valid DeleteAccount.Request request
    ) {
    	return DeleteAccount.Response.from(
    			accountService.deleteAccount(
    					request.getUserId(),
    					request.getAccountNumber()
    					)
    	);
    }
    
    @GetMapping("/account")
    public List<AccountInfo> getAccountsByUserId(
    		@RequestParam("user_id") Long userId
    		){
    	return accountService.getAccountsByUserId(userId)
    			.stream().map(accountDto -> 
    					AccountInfo.builder()
    					.accountNumber(accountDto.getAcoountNumber())
    					.balance(accountDto.getBalance()).build())
    			.collect(Collectors.toList());
    	
    }
    
	
	@GetMapping("/get-lock")
	public String getLock() {
		return redisTestServive.getLock();
	}
	
	
    @GetMapping("/account/{id}")
    public Account getAccount(
    		@PathVariable Long id) {
        return accountService.getAccount(id);
    }

    
}

