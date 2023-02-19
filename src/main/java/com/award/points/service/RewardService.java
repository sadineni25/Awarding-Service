package com.award.points.service;

import com.award.points.payload.RewardPointsDto;
import com.award.points.payload.TransactionDetailsDto;

import java.util.List;

public interface RewardService {


    public Long calculateRewardPoints(List<TransactionDetailsDto> transactionDetails, String customerId);

    public Long calculateRewardPointsForMonth(List<TransactionDetailsDto> transactionDetails,
                                              String customerId,
                                              String month);

    public List<RewardPointsDto> getEachCustomerRewardPoints(List<TransactionDetailsDto> transactionDetails);

}
