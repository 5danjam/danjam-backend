package com.danjam.review.querydsl;

import com.danjam.booking.QBooking;
import com.danjam.dorm.QDorm;
import com.danjam.review.QReview;
import com.danjam.review.ReviewDto;
import com.danjam.users.QUsers;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

public class ReviewRepositoryCustomImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<ReviewDto> findReviewsByDormId(Long dormId) {
        QReview qReview = QReview.review;
        QUsers qUsers = QUsers.users;
        QDorm qDorm = QDorm.dorm;
        QBooking qBooking = QBooking.booking;

        return queryFactory
                .select(Projections.constructor(ReviewDto.class,
                        qReview.id,
                        qReview.content,
                        qReview.rate,
                        qReview.users.id.as("userId"),
                        qReview.booking.id.as("bookingId"),
                        qReview.createAt,
                        qReview.updateAt,
                        qReview.users.email.as("email")
                        ))
                .from(qReview)
                .join(qReview.booking, qBooking)
                .join(qBooking.room.dorm, qDorm)
                .where(qDorm.id.eq(dormId))
                .fetch();
    }

}
