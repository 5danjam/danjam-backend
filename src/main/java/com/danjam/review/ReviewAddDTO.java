package com.danjam.review;

import com.danjam.booking.Booking;
import com.danjam.users.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewAddDTO {
    private String content;
    private double rate;
    private Long user_id;
    private Long booking_id;
}
