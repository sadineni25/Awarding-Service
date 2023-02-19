package com.award.points.payload;

import lombok.Data;

import java.util.List;

@Data
public class RewardPointsDto {

    String customerId;
    Long totalRewardPoints;

    List<MonthlyRewardPointsDto> monthlyRewardPoints;
}
