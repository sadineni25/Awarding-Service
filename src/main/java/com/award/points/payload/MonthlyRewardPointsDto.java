package com.award.points.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * MonthlyRewardPoints data transfer object to communicate via REST calls
 *
 */
@Data
@AllArgsConstructor
public class MonthlyRewardPointsDto {

    String month;
    Long rewardPoints;
}
