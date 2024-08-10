package com.danjam.dorm;

import com.danjam.booking.Booking;
import com.danjam.booking.BookingDTOD;
import com.danjam.room.Room;
import com.danjam.room.RoomDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
public class DormBookingListDTO {

    private Long id;
    private String name;
    private String address;
    private String status;
    private RoomDTO room;
    private BookingDTOD booking;

    @Builder
    public DormBookingListDTO(Long id, String name, String address, String status, RoomDTO room, BookingDTOD booking) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.status = status;
        this.room = room;
        this.booking = booking;
    }


}
