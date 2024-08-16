package com.danjam.review;

import com.danjam.booking.Booking;
import com.danjam.users.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.relational.core.sql.In;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long id;
    private Long userId;
    private String email;
    private Long bookingId;
    private String content;
    private Users users;
    private Booking booking;
    private double rate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;



    public ReviewDto(Long id, String content, Double rate, Long userId, Long bookingId, LocalDateTime createdAt,
                     LocalDateTime updatedAt, String email) {
        this.id = id;
        this.content = content;
        this.rate = rate;
        this.userId = userId;
        this.bookingId = bookingId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.email = email;
    }

    public Review toEntity() {
        return Review.builder()
                .content(content)
                .users(users)
                .booking(booking)
                .createAt(createdAt)
                .updateAt(updatedAt)
                .rate(rate)
                .build();
    }
}
