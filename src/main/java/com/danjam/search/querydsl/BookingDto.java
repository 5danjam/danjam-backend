package com.danjam.search.querydsl;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BookingDto {
    private Long id;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private RoomDto room;

    @Builder
    public BookingDto(Long id, LocalDateTime checkIn, LocalDateTime checkOut, RoomDto room) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.room = room;
    }
}
