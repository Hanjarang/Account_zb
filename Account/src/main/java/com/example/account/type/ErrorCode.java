package com.example.account.type;


import lombok.Getter;
import lombok.AllArgsConstructor;



@Getter
@AllArgsConstructor
public enum ErrorCode {

	USER_NOT_FOUND("사용자가 없습니다."),
	ACCOUNT_NOT_FOUND("계좌가 없습니다."),
	USER_ACCOUNT_UNMATCH("사용자와 계좌가 일치하지않습니다."),
	ACCOUNT_ALREADY_UNREGISTERED("이미 해지된 계좌입니다."),
	BALANCE_NOT_EMPTY("잔액이 있는 계좌는 해지할 수 없습니다."),
	AMOUNT_EXCEED_BALANCE("거래 금액이 계좌 잔액보다 큽니다."),
	MAX_ACCOUNT_PER_USER_10("사용자 최대 계좌는 10개입니다.");
	
	private final String description;
	
}
