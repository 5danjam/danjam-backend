package com.danjam.booking;

import com.danjam.payment.Payment;
import com.danjam.room.Room;
import com.danjam.users.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private Long id;
    private Users users;
    private Room room;
    private Payment payment;
    private int person;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String status;

    public Booking toEntity() {
        return Booking.builder()
                .id(id)
                .users(users)
                .room(room)
                .payment(payment)
                .person(person)
                .checkIn(checkIn)
                .checkOut(checkOut)
                .status(status)
                .build();
    }
}
