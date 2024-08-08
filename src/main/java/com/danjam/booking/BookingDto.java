package com.danjam.booking;

import com.danjam.payment.Payment;
import com.danjam.room.Room;
import com.danjam.users.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {
    private long id;
    private Users users;
    private Room room;
    private Payment payment;
    private int person;
    private Date checkIn;
    private Date checkOut;
    private String status;

    public Booking toEntity() {
        return Booking.builder()
                .id(id)
                .users(users)
                .room(room)
                .payment(payment)
                .person(person)
                .check_in(checkIn)
                .check_out(checkOut)
                .status(status)
                .build();
    }
}
