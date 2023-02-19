package com.award.points.payload;

import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionDetailsDto {
    @NotNull
    @NotEmpty(message = "Transaction id should not be null or empty")
    String transactionId;
    @NotNull
    @NotEmpty(message = "Customer id should not be null or empty")
    String customerId;

    @NotNull
    Double amount;

    @NotNull
    @DateTimeFormat(pattern = "YYYY-MM-dd")
    Date transactionDate;
}
