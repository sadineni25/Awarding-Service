package com.award.points.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthlyRewardPointsDto {

    String month;
    Long rewardPoints;
}
