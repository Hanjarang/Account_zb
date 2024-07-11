package com.example.account.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

import lombok.*;

public class CreateAccount {
	
	@Getter
	@Setter
	@AllArgsConstructor
	public static class Request{
		
		@NotNull
		@Min(1)
		private Long userId;
		
		@NotNull
		@Min(0)
		private Long initialBalance;
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	@Builder
	public static class Response{
		private Long userId;
		private String accountNumber;
		private LocalDateTime registeredAt;
		
		public static Response from(AccountDto accountDto) {
			return Response.builder()
						   .userId(accountDto.getUserId())
						   .accountNumber(accountDto.getAcoountNumber())
						   .registeredAt(accountDto.getRegisteredAt())
						   .build();
		}
	}
}
