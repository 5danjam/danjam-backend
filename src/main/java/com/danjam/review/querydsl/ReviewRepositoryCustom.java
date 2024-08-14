package com.danjam.review.querydsl;

import com.danjam.review.ReviewDto;

import java.util.List;

public interface ReviewRepositoryCustom {

    List<ReviewDto> findReviewsByDormId(Long dormId);

}
