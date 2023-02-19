package com.award.points.controller;

import com.award.points.payload.RewardPointsDto;
import com.award.points.payload.TransactionDetailsDto;
import com.award.points.service.RewardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@Validated
public class TransactionController {

    @Autowired
    RewardService rewardService;

    @PostMapping()
    public ResponseEntity<List<RewardPointsDto>> calculateRewardPointsForEachCustomer(@Valid @RequestBody List<TransactionDetailsDto> transactions) {
        return new ResponseEntity<>(rewardService.getEachCustomerRewardPoints(transactions), HttpStatus.OK);
    }

}
