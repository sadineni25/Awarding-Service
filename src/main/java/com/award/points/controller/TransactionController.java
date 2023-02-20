package com.award.points.controller;

import com.award.points.payload.RewardPointsDto;
import com.award.points.payload.TransactionDetailsDto;
import com.award.points.service.RewardService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The TransactionController class to manage reward points for each customer.
 *
 */
@RestController
@RequestMapping("/api/v1/transactions")
@Validated
public class TransactionController {

    /**
     * Logger to log messages to console and log file.
     */
    Logger logger = LoggerFactory.getLogger(TransactionController.class);

    /**
     * RewardService to calculate reward points for given transactions.
     */
    @Autowired
    RewardService rewardService;

    /**
     * Calculate reward points for all customers exists in given transactions.
     *
     * @param  transactions   list of transactions
     * @return                reward points earned for each customer per month and total.
     */
    @PostMapping()
    public ResponseEntity<List<RewardPointsDto>> calculateRewardPointsForEachCustomer(@Valid @RequestBody List<TransactionDetailsDto> transactions) {
        logger.debug("Received {} transactions for processing", transactions.size());
        return new ResponseEntity<>(rewardService.getEachCustomerRewardPoints(transactions), HttpStatus.OK);
    }

}
