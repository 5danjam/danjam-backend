package com.danjam.review.querydsl;

import com.danjam.review.DormStatsDTO;
import com.danjam.review.ReviewDto;
import com.danjam.review.StatsAndReviewDTO;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<ReviewDto> findReviewsByDormId(Long dormId);

    DormStatsDTO calculateDormReviewStats(Long dormId);

    StatsAndReviewDTO makeStatsAndReviews(Long dormId);

    Double calculateAverageRate(Long dormId);

}
