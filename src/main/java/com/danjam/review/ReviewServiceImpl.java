package com.danjam.review;

import com.danjam.booking.Booking;
import com.danjam.booking.BookingRepository;
import com.danjam.users.Users;
import com.danjam.users.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl  {
    private final UsersRepository USERSREPOSITORY;
    private final BookingRepository BOOKINGREPOSITORY;
    private final ReviewRepository REVIEWREPOSITORY;

    @Transactional
    public Long write(ReviewAddDTO reviewAddDTO) {
        Users user = USERSREPOSITORY.findById(reviewAddDTO.getUser_id()).orElseThrow(() -> new RuntimeException("User not found"));
        Booking booking = BOOKINGREPOSITORY.findById(reviewAddDTO.getBooking_id()).orElseThrow(() -> new RuntimeException("Booking not found"));

            Review review = Review.builder()
                    .content(reviewAddDTO.getContent())
                    .rate(reviewAddDTO.getRate())
                    .booking(booking)
                    .users(user)
                    .build();

            return REVIEWREPOSITORY.save(review).getId();

    }

    public List<ReviewDto> getReviewsByDormId(Long dormId) {
        List<ReviewDto> reviews = REVIEWREPOSITORY.findReviewsByDormId(dormId);
        System.out.println("reviews = " + reviews);

        return reviews;
    }

    public DormStatsDTO getDormStatsByDormId(Long dormId) {
        DormStatsDTO dormStats = REVIEWREPOSITORY.calculateDormReviewStats(dormId);
        System.out.println("dormStats = " + dormStats);

        return dormStats;
    }

    public StatsAndReviewDTO getStatsAndReviews(Long dormId) {
        return REVIEWREPOSITORY.makeStatsAndReviews(dormId);
    }

}
