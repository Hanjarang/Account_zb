package com.example.account.service;

import com.example.account.domain.Account;
import com.example.account.domain.AccountUser;
import com.example.account.dto.AccountDto;
import com.example.account.exception.AccountException;
//import com.example.account.exception.AccountException;
import com.example.account.repository.AccountRepository;
import com.example.account.repository.AccountUserRepository;
import com.example.account.type.AccountStatus;
import com.example.account.type.ErrorCode;

import org.junit.jupiter.api.DisplayName;
//import com.example.account.type.AccountStatus;
//import com.example.account.type.ErrorCode;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//import java.util.Arrays;
//import java.util.List;
import java.util.Optional;

//import static com.example.account.type.ErrorCode.USER_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    private AccountRepository accountRepository;

    @Mock
    private AccountUserRepository accountUserRepository;

    @InjectMocks
    private AccountService accountService;

    
    @Test
    void createAccountSuccess() {
    	//given
        AccountUser user = AccountUser.builder()
                .Id(12L)
                .name("Pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));
        given(accountRepository.findFirstByOrderByIdDesc())
        		.willReturn(Optional.of(Account.builder()
        						.accountNumber("1000000012").build()));
        
        given(accountRepository.save(any()))
        		.willReturn(Account.builder()
        				.accountUser(user)
        				.accountNumber("10000000015").build());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        // when
        AccountDto accountDto = accountService.createAccount(1L, 1000L);
        
        //then
        verify(accountRepository, times(1)).save(captor.capture());
        assertEquals(12L, accountDto.getUserId());
        assertEquals("1000000013", captor.getValue().getAccountNumber());
    }

    
    @Test
    void deleteAccountSuccess() {
    	//given
        AccountUser user = AccountUser.builder()
                .Id(12L)
                .name("Pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));
        given(accountRepository.findByAccountNumber(anyString()))
        		.willReturn(Optional.of(Account.builder()
        						.accountUser(user)
        						.balance(0L)
        						.accountNumber("1000000012").build()));

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        // when
        AccountDto accountDto = accountService.deleteAccount(1L, "1234567890");
        
        //then
        verify(accountRepository, times(1)).save(captor.capture());
        assertEquals(12L, accountDto.getUserId());
        assertEquals("1000000012", captor.getValue().getAccountNumber());
        assertEquals(AccountStatus.UNREGISTERED, captor.getValue().getAccountStatus());
    }

    @Test
    void createFirstAccount() {
    	//given
        AccountUser user = AccountUser.builder()
                .Id(15L)
                .name("Pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));
        given(accountRepository.findFirstByOrderByIdDesc())
        		.willReturn(Optional.empty());
        						
        given(accountRepository.save(any()))
        		.willReturn(Account.builder()
        				.accountUser(user)
        				.accountNumber("10000000015").build());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        // when
        AccountDto accountDto = accountService.createAccount(1L, 1000L);
        
        //then
        verify(accountRepository, times(1)).save(captor.capture());
        assertEquals(15L, accountDto.getUserId());
        assertEquals("1000000000", captor.getValue().getAccountNumber());
    }
    

    @Test
    @DisplayName("해당 유저 없음 - 계좌 생성 실패")
    void createAccount_UserNotFound() {
    	//given
        AccountUser user = AccountUser.builder()
                .Id(15L)
                .name("Pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        // when
        AccountException exception = assertThrows(AccountException.class,
        		()-> accountService.createAccount(1L, 1000L));
        
        //then
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }
    
    @Test
    @DisplayName("유저 당 최대 계좌는 10개")
    void createAccount_maxAccountIs10() {
    	//given
    	AccountUser user = AccountUser.builder()
                .Id(15L)
                .name("Pobi")
                .build();
    	
        given(accountUserRepository.findById(anyLong()))
        		.willReturn(Optional.of(user));
        
        given(accountRepository.countByAccountUser(any()))
				.willReturn(10);

        //when
        AccountException exception = assertThrows(AccountException.class,
        		()-> accountService.createAccount(1L, 1000L));
        
        //then
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        
    }
    
    @Test
    @DisplayName("해당 유저 없음 - 계좌 해지 실패")
    void deleteAccount_UserNotFound() {
    	//given
        AccountUser user = AccountUser.builder()
                .Id(15L)
                .name("Pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        ArgumentCaptor<Account> captor = ArgumentCaptor.forClass(Account.class);

        // when
        AccountException exception = assertThrows(AccountException.class,
        		()-> accountService.deleteAccount(1L, "1234567890"));
        
        //then
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
    }
    
    @Test
    @DisplayName("해당 계좌 없음 - 계좌 해지 실패")
    void deleteAccount_AccountNotFound() {
    	//given
        AccountUser user = AccountUser.builder()
                .Id(15L)
                .name("Pobi")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(user));
        given(accountRepository.findByAccountNumber(anyString()))
        		.willReturn(Optional.empty());


     // when
        AccountException exception = assertThrows(AccountException.class,
        		()-> accountService.deleteAccount(1L, "1234567890"));
        
        //then
        assertEquals(ErrorCode.ACCOUNT_NOT_FOUND, exception.getErrorCode());
    }
    
    @Test
    @DisplayName("계좌 소유주가 일치하지 않습니다.")
    void deleteAccountFailed_userUnMatch() {
    	//given
        AccountUser pobi = AccountUser.builder()
                .Id(12L)
                .name("Pobi")
                .build();
        AccountUser harry = AccountUser.builder()
                .Id(13L)
                .name("Harry")
                .build();

        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(pobi));
        given(accountRepository.findByAccountNumber(anyString()))
        		.willReturn(Optional.of(Account.builder()
        						.accountUser(harry)
        						.balance(0L)
        						.accountNumber("1000000012").build()));
        
     // when
        AccountException exception = assertThrows(AccountException.class,
        		()-> accountService.deleteAccount(1L, "1234567890"));
        
        //then
        assertEquals(ErrorCode.USER_ACCOUNT_UNMATCH, exception.getErrorCode());

        
    }
    
    @Test
    @DisplayName("해지 계좌는 잔액이 없어야 합니다.")
    void deleteAccountFailed_balanceNotEmpty() {
    	//given
        AccountUser pobi = AccountUser.builder()
                .Id(12L)
                .name("Pobi")
                .build();
        
        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(pobi));
        given(accountRepository.findByAccountNumber(anyString()))
        		.willReturn(Optional.of(Account.builder()
        						.accountUser(pobi)
        						.balance(100L)
        						.accountNumber("1000000012").build()));
        
     // when
        AccountException exception = assertThrows(AccountException.class,
        		()-> accountService.deleteAccount(1L, "1234567890"));
        
        //then
        assertEquals(ErrorCode.BALANCE_NOT_EMPTY, exception.getErrorCode());

        
    }
    
    @Test
    @DisplayName("해당 계좌는 이미 해지된 계좌입니다.")
    void deleteAccountFailed_alreadyUnregistered() {
    	//given
        AccountUser pobi = AccountUser.builder()
                .Id(12L)
                .name("Pobi")
                .build();
        
        given(accountUserRepository.findById(anyLong()))
                .willReturn(Optional.of(pobi));
        given(accountRepository.findByAccountNumber(anyString()))
        		.willReturn(Optional.of(Account.builder()
        						.accountUser(pobi)
        						.accountStatus(AccountStatus.UNREGISTERED)
        						.balance(0L)
        						.accountNumber("1000000012").build()));
        
     // when
        AccountException exception = assertThrows(AccountException.class,
        		()-> accountService.deleteAccount(1L, "1234567890"));
        
        //then
        assertEquals(ErrorCode.ACCOUNT_ALREADY_UNREGISTERED, exception.getErrorCode());

        
    }
    
    



  

}