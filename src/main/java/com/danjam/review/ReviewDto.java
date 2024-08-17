package com.danjam.review;

import com.danjam.booking.Booking;
import com.danjam.users.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private long id;
    private String content;
    private Users users;
    private Booking booking;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Review toEntity() {
        return Review.builder()
                .content(content)
                .users(users)
                .booking(booking)
                .createAt(createdAt)
                .updateAt(updatedAt)
                .build();
    }
}
