package com.danjam.search.querydsl;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private double rate;
//    private BookingDto bookingDto;

    @Builder
    public ReviewDto(Long id, double rate) {
//    public ReivewDto(Long id, double rate, BookingDto bookingDto) {
        this.id = id;
        this.rate = rate;
//        this.bookingDto = bookingDto;
    }
}
