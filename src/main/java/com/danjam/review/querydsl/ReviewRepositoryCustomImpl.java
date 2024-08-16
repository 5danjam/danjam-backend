package com.danjam.review.querydsl;

import com.danjam.booking.QBooking;
import com.danjam.dorm.QDorm;
import com.danjam.r_tag.QRtag;
import com.danjam.review.DormStatsDTO;
import com.danjam.review.QReview;
import com.danjam.review.ReviewDto;
import com.danjam.review.StatsAndReviewDTO;
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

    @Override
    public StatsAndReviewDTO makeStatsAndReviews(Long dormId) {
        DormStatsDTO dormStatsDTO = calculateDormReviewStats(dormId);
        List<ReviewDto> reviewDtos = findReviewsByDormId(dormId);

        return new StatsAndReviewDTO(reviewDtos, dormStatsDTO);
    }

    @Override
    public Double calculateAverageRate(Long dormId) {
        QReview qReview = QReview.review;
        QDorm qDorm = QDorm.dorm;
        QBooking qBooking = QBooking.booking;

        Double averageRate = queryFactory
                .select(qReview.rate.avg())
                .from(qReview)
                .join(qReview.booking, qBooking)
                .join(qBooking.room.dorm, qDorm)
                .where(qDorm.id.eq(dormId))
                .fetchOne();

        if (averageRate != null) {
            averageRate = Math.round(averageRate * 100.0) / 100.0;
        } else {
            averageRate = 0.0;
        }

        return averageRate;
    }


    @Override
    public DormStatsDTO calculateDormReviewStats(Long dormId) {

        int cleanlinessScoreRow = getTagCount(dormId, 1L) - getTagCount(dormId, 2L);
        int communicationScoreRow = getTagCount(dormId, 3L) - getTagCount(dormId, 4L);
        int locationScoreRow = getTagCount(dormId, 5L) - getTagCount(dormId, 6L);
        int accuracyScoreRow = getTagCount(dormId, 7L) - getTagCount(dormId, 8L);
        int checkInScoreRow = getTagCount(dormId, 9L) - getTagCount(dormId, 10L);
        int costEffectivenessScoreRow = getTagCount(dormId, 11L) - getTagCount(dormId, 12L);
        int kindnessScoreRow = getTagCount(dormId, 13L) - getTagCount(dormId, 14L);

        Double totalCleanlinessScore = normalizeScore(cleanlinessScoreRow);
        Double totalLocationScore = normalizeScore(locationScoreRow);
        Double totalAccuracyScore = normalizeScore(accuracyScoreRow);
        Double totalCheckInScore = normalizeScore(checkInScoreRow);
        Double totalCommunicationScore = normalizeScore(communicationScoreRow);
        Double totalCostEffectivenessScore = normalizeScore(costEffectivenessScoreRow);
        Double totalKindnessScore = normalizeScore(kindnessScoreRow);
        Double rateAverage = calculateAverageRate(dormId);

        DormStatsDTO result = new DormStatsDTO(dormId, totalCleanlinessScore, totalAccuracyScore, totalCheckInScore,
                totalCommunicationScore, totalLocationScore, totalCostEffectivenessScore, totalKindnessScore, rateAverage);

        System.out.println("result = " + result);

        return result;
    }

    private Integer getTagCount(Long dormId, Long tagId) {

        QRtag qRtag = QRtag.rtag;
        QReview qReview = QReview.review;
        QDorm qDorm = QDorm.dorm;
        QBooking qBooking = QBooking.booking;

        Long result = queryFactory
                .select(qRtag.count())
                .from(qRtag)
                .join(qRtag.review, qReview)
                .join(qReview.booking, qBooking)
                .join(qBooking.room.dorm, qDorm)
                .where(qDorm.id.eq(dormId).and(qRtag.tag.id.eq(tagId)))
                .fetchOne();

        int count = result != null ? Math.toIntExact(result) : 0;

        return count;
    }

    private double normalizeScore (int score) {
        return (double) score / 100 * 5.0;
    }

}
