package com.example.account.domain;



import lombok.*;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class AccountUser {

	@Id
	@GeneratedValue
	private Long Id;
	
	private String name;
	
	@CreatedDate
	private LocalDateTime createdAt;
	@LastModifiedDate
	private LocalDateTime updatedAt;
}
