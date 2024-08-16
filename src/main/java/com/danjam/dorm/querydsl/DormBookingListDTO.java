package com.danjam.dorm.querydsl;

import com.danjam.booking.querydsl.DormBookingDTO;
import com.danjam.room.RoomDTO;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class DormBookingListDTO {

    private Long id;
    private String name;
    private String address;
    private String status;
    private RoomDTO room;
    private DormBookingDTO booking;

    @Builder
    public DormBookingListDTO(Long id, String name, String address, String status, RoomDTO room, DormBookingDTO booking) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.status = status;
        this.room = room;
        this.booking = booking;
    }


}
