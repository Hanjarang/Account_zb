package com.example.account.dto;

import com.example.account.domain.Account;
import java.time.LocalDateTime;


import lombok.*;




@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {
	
	private Long userId;
	private String acoountNumber;
	private Long balance;
	
	private LocalDateTime registeredAt;
	private LocalDateTime unRegisteredAt;
	
	public static AccountDto fromEntity(Account account) {
		return AccountDto.builder()
						 .userId(account.getAccountUser().getId())
						 .acoountNumber(account.getAccountNumber())
						 .registeredAt(account.getRegisteredAt())
						 .unRegisteredAt(account.getUnRegisteredAt())
						 .build();
	}


}
