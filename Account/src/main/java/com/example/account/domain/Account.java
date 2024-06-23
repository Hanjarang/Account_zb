package com.example.account.domain;

import lombok.*;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.account.type.AccountStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Account {
    
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private AccountUser accountUser;
	private String accountNumber;
	
	@Enumerated(EnumType.STRING)
	private AccountStatus accountStatus;
	private Long balance;
	
	private LocalDateTime registeredAt;
	private LocalDateTime unRegisteredAt;
	
	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;

	
}
