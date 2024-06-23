package com.example.account.controller;

import com.example.account.domain.Account;
import com.example.account.dto.AccountDto;
import com.example.account.dto.CreateAccount;
import com.example.account.dto.CreateAccount.Response;
import com.example.account.service.AccountService;
import com.example.account.service.RedisTestService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

