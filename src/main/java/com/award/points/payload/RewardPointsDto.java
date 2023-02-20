package com.award.points.payload;

import lombok.Data;

import java.util.List;

/**
 * RewardPoints data transfer object to communicate via REST calls
 *
 */
@Data
public class RewardPointsDto {

    String customerId;
    Long totalRewardPoints;

    List<MonthlyRewardPointsDto> monthlyRewardPoints;
}
