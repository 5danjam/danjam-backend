package com.danjam.review;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatsAndReviewDTO {

    private List<ReviewDto> reviews;
    private DormStatsDTO stats;

}
