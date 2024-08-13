package com.danjam.search.querydsl;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BookingDto {
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    // private RoomDto room;

    @Builder
    public BookingDto(Long id, LocalDate checkIn, LocalDate checkOut) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
}
