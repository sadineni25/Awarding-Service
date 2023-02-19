package com.award.points.service.impl;

import com.award.points.payload.MonthlyRewardPointsDto;
import com.award.points.payload.RewardPointsDto;
import com.award.points.payload.TransactionDetailsDto;
import com.award.points.service.RewardService;
import com.award.points.utils.DateUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RewardServiceImpl implements RewardService {



    @Override
    public Long calculateRewardPoints(List<TransactionDetailsDto> transactionDetails, String customerId) {
        Long rewardPoints = 0L;
        for (TransactionDetailsDto transaction : transactionDetails) {
            if (transaction.getAmount() > 50 && transaction.getCustomerId().equals(customerId)) {
                Integer amount = transaction.getAmount().intValue();
                rewardPoints += (amount - 50);
                if (amount > 100) {
                    rewardPoints += (amount - 100);
                }
            }
        }

        return rewardPoints;
    }

    @Override
    public Long calculateRewardPointsForMonth(List<TransactionDetailsDto> transactionDetails,
                                              String customerId,
                                              String monthAndYear) {

        Long rewardPoints = 0L;
        for (TransactionDetailsDto transaction : transactionDetails) {
            if (transaction.getAmount() > 50
                    && transaction.getCustomerId().equals(customerId) &&
                    DateUtils.getMonthAndYear(transaction.getTransactionDate()).equals(monthAndYear)) {
                Integer amount = transaction.getAmount().intValue();
                rewardPoints += (amount - 50);
                if (amount > 100) {
                    rewardPoints += (amount - 100);
                }
            }
        }

        return rewardPoints;
    }


    @Override
    public List<RewardPointsDto> getEachCustomerRewardPoints(List<TransactionDetailsDto> transactionDetails) {

        Map<String, List<TransactionDetailsDto>> customerTransactions = transactionDetails.stream()
                .collect(Collectors.groupingBy(TransactionDetailsDto::getCustomerId));

        List<RewardPointsDto> customerRewardPoints = new ArrayList<>();
        for (Map.Entry<String, List<TransactionDetailsDto>> entry : customerTransactions.entrySet()) {

            RewardPointsDto rewardPointsDto = new RewardPointsDto();
            rewardPointsDto.setCustomerId(entry.getKey());
            rewardPointsDto.setTotalRewardPoints(calculateRewardPoints(entry.getValue(), entry.getKey()));
            rewardPointsDto.setMonthlyRewardPoints(new ArrayList<>());

            Set<String> monthlyRewardPoints = new HashSet<>();
            for (TransactionDetailsDto transaction : entry.getValue()) {
                String monthAndYear = DateUtils.getMonthAndYear(transaction.getTransactionDate());
                if (!monthlyRewardPoints.contains(monthAndYear)) {
                    rewardPointsDto.getMonthlyRewardPoints().add(new MonthlyRewardPointsDto(monthAndYear,
                            calculateRewardPointsForMonth(entry.getValue(), entry.getKey(), monthAndYear)));
                    monthlyRewardPoints.add(monthAndYear);
                }
            }

            customerRewardPoints.add(rewardPointsDto);
        }

        return customerRewardPoints;
    }
}
