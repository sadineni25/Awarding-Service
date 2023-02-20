package com.award.points.service.impl;

import com.award.points.payload.MonthlyRewardPointsDto;
import com.award.points.payload.RewardPointsDto;
import com.award.points.payload.TransactionDetailsDto;
import com.award.points.service.RewardService;
import com.award.points.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * RewardService impl class to handle reward calculation logic.
 *
 */
@Service
public class RewardServiceImpl implements RewardService {

    /**
     * Logger to log messages to console and log file.
     */
    Logger logger = LoggerFactory.getLogger(RewardServiceImpl.class);


    /**
     * Calculate reward points for all customers exists in given transactions.
     *
     * @param  transactionDetails   list of transactions
     * @Param  customerId           customer id
     * @return                      total reward points earned for given customer.
     */
    @Override
    public Long calculateRewardPoints(List<TransactionDetailsDto> transactionDetails, String customerId) {
        Long rewardPoints = 0L;
        logger.debug("Calculating reward points for customer {}", customerId);
        for (TransactionDetailsDto transaction : transactionDetails) {
            Long rewardPointsForTransaction = 0L;
            if (transaction.getAmount() > 50 && transaction.getCustomerId().equals(customerId)) {
                Integer amount = transaction.getAmount().intValue();
                rewardPointsForTransaction += (amount - 50);
                if (amount > 100) {
                    rewardPointsForTransaction += (amount - 100);
                }

                logger.trace("Calculated reward points for {} are {}",
                        transaction.getAmount(), rewardPointsForTransaction);
                rewardPoints += rewardPointsForTransaction;
            }
        }
        logger.debug("Customer {} earned total {} reward points", customerId, rewardPoints);

        return rewardPoints;
    }

    /**
     * Calculate reward points for all customers exists in given transactions.
     *
     * @param  transactionDetails   list of transactions
     * @param  customerId           customer id
     * @param  monthAndYear                month and year as string
     * @return                      reward points earned for given customer on given month and year.
     */
    @Override
    public Long calculateRewardPointsForMonth(List<TransactionDetailsDto> transactionDetails,
                                              String customerId,
                                              String monthAndYear) {

        logger.debug("Calculating reward points of month {} for customer {} ", monthAndYear, customerId);
        Long rewardPoints = 0L;
        for (TransactionDetailsDto transaction : transactionDetails) {
            Long rewardPointsForTransaction = 0L;
            if (transaction.getAmount() > 50
                    && transaction.getCustomerId().equals(customerId) &&
                    DateUtils.getMonthAndYear(transaction.getTransactionDate()).equals(monthAndYear)) {
                Integer amount = transaction.getAmount().intValue();
                rewardPointsForTransaction += (amount - 50);
                if (amount > 100) {
                    rewardPointsForTransaction += (amount - 100);
                }

                logger.trace("Calculated reward points for {} are {}",
                        transaction.getAmount(), rewardPointsForTransaction);
                rewardPoints += rewardPointsForTransaction;
            }
        }
        logger.debug("Customer {} earned total {} reward points in month {}",customerId, rewardPoints, monthAndYear);

        return rewardPoints;
    }


    /**
     * Calculate reward points for all customers exists in given transactions.
     *
     * @param  transactionDetails   list of transactions
     * @return                      reward points earned for each customer per month and total.
     */
    @Override
    public List<RewardPointsDto> getEachCustomerRewardPoints(List<TransactionDetailsDto> transactionDetails) {

        Map<String, List<TransactionDetailsDto>> customerTransactions = transactionDetails.stream()
                .collect(Collectors.groupingBy(TransactionDetailsDto::getCustomerId));

        logger.debug("{} noOf customer's transactions", customerTransactions.size());

        List<RewardPointsDto> customerRewardPoints = new ArrayList<>();
        for (Map.Entry<String, List<TransactionDetailsDto>> entry : customerTransactions.entrySet()) {

            logger.debug("Calculating reward points of customer {} for {} transactions",
                    entry.getKey(), entry.getValue().size());

            RewardPointsDto rewardPointsDto = new RewardPointsDto();
            rewardPointsDto.setCustomerId(entry.getKey());
            rewardPointsDto.setTotalRewardPoints(calculateRewardPoints(entry.getValue(), entry.getKey()));
            rewardPointsDto.setMonthlyRewardPoints(new ArrayList<>());

            Set<String> monthlyRewardPoints = new HashSet<>();
            for (TransactionDetailsDto transaction : entry.getValue()) {
                String monthAndYear = DateUtils.getMonthAndYear(transaction.getTransactionDate());
                if (!monthlyRewardPoints.contains(monthAndYear)) {
                    logger.trace("Calculating reward points of customer {} for {} ", entry.getKey(), monthAndYear);
                    rewardPointsDto.getMonthlyRewardPoints().add(new MonthlyRewardPointsDto(monthAndYear,
                            calculateRewardPointsForMonth(entry.getValue(), entry.getKey(), monthAndYear)));
                    monthlyRewardPoints.add(monthAndYear);
                }
            }

            customerRewardPoints.add(rewardPointsDto);
        }
        logger.debug("Calculated reward points for total {} customers", customerRewardPoints.size());

        return customerRewardPoints;
    }
}
