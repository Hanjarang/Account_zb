package com.example.account.dto;
import com.example.account.type.TransactionResultType;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;

public class UseBalance {
  /**
   * {
   *   "useId":1,
   *   "accountNumber":"1000000000":
   *   "amount":1000
   * }
   */
  @Getter
  @Setter
  @AllArgsConstructor
  @NoArgsConstructor
  public static class Request {
    @NotNull
    @Min(1)
    private Long userId;

    @NotBlank
    @Size(min = 10, max = 10)
    private String accountNumber;

    @NotNull
    @Min(10)
    @Max(1000_000_000)
    private Long amount;
  }

  /**
   * {
   *   "accountNumber":"1234567890",
   *   "transactionResult":"S",
   *   "transactionId":"c2033bb6d",
   *   "amount":1000,
   *   "transactedAt":"2024-06-11~~~"
   * }
   */
  @Getter
  @Setter
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Response {
    private String accountNumber;
    private TransactionResultType transactionResult;
    private String transactionId;
    private Long amount;
    private LocalDateTime transactedAt;

    public static Response from(TransactionDto transactionDto) {
      return Response.builder()
          .accountNumber(transactionDto.getAccountNumber())
          .transactionResult(transactionDto.getTransactionResultType())
          .transactionId(transactionDto.getTransactionId())
          .amount(transactionDto.getAmount())
          .transactedAt(transactionDto.getTransactedAt())
          .build();
    }
  }
}
