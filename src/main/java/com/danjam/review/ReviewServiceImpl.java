package com.danjam.review;

import com.danjam.booking.Booking;
import com.danjam.booking.BookingRepository;
import com.danjam.users.Users;
import com.danjam.users.UsersRepository;
import com.danjam.wish.querydsl.WishWithSliceResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


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

    @Transactional
    public Slice<ReviewDto> findReviewsByDormId(Long dormId, Pageable pageable) {
        Page<Review> reviewPage = REVIEWREPOSITORY.findReviewsByDormId(dormId, pageable);
        List<ReviewDto> reviewDtoList = reviewPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return new SliceImpl<>(reviewDtoList, pageable, reviewPage.hasNext());
    }

    private ReviewDto convertToDto(Review review) {
        return new ReviewDto(
                review.getId(),
                review.getContent(),
                review.getBooking().getUsers(),
                review.getBooking(),
                review.getCreateAt(),
                review.getUpdateAt()
        );
    }
//    public Slice<ReviewDto> findReviewsByDormId(Long id, Pageable pageable) {
//        List<ReviewDto> reviews = REVIEWREPOSITORY.findReviewsByDormId(id, pageable);
//
//        boolean hasNext = hasNextPage(reviews, pageable.getPageSize());
//
//        return new SliceImpl<>(
//                reviews,
//                pageable,
//                hasNext
//        );
//    }
//
//    private boolean hasNextPage(List<ReviewDto> reviews, int pageSize) {
//        if (reviews.size() > pageSize) {
//            reviews.remove(pageSize);
//            return true;
//        }
//        return false;
//    }
}
