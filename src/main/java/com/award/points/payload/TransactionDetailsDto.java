package com.award.points.payload;

import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

/**
 * TransactionDetails data transfer object to communicate via REST calls
 *
 */
@Data
public class TransactionDetailsDto {
    @NotEmpty(message = "transactionId should not be null or empty")
    String transactionId;
    @NotEmpty(message = "customerId should not be null or empty")
    String customerId;

    @NotNull
    Double amount;

    @NotNull(message = "transactionDate should be in yyyy-MM-dd format")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date transactionDate;
}
