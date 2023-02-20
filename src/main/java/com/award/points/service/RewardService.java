package com.award.points.service;

import com.award.points.payload.RewardPointsDto;
import com.award.points.payload.TransactionDetailsDto;

import java.util.List;

/**
 * RewardService class with reward calculations logic templates
 *
 */
public interface RewardService {


    /**
     * Calculate reward points for all customers exists in given transactions.
     *
     * @param  transactionDetails   list of transactions
     * @Param  customerId           customer id
     * @return                      total reward points earned for given customer.
     */
    public Long calculateRewardPoints(List<TransactionDetailsDto> transactionDetails, String customerId);

    /**
     * Calculate reward points for all customers exists in given transactions.
     *
     * @param  transactionDetails   list of transactions
     * @param  customerId           customer id
     * @param  monthAndYear         month and year as string
     * @return                      reward points earned for given customer on given month and year.
     */
    public Long calculateRewardPointsForMonth(List<TransactionDetailsDto> transactionDetails,
                                              String customerId,
                                              String monthAndYear);

    /**
     * Calculate reward points for all customers exists in given transactions.
     *
     * @param  transactionDetails   list of transactions
     * @return                      reward points earned for each customer per month and total.
     */
    public List<RewardPointsDto> getEachCustomerRewardPoints(List<TransactionDetailsDto> transactionDetails);

}
