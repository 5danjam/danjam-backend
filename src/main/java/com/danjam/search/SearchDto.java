package com.danjam.search;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
public class SearchDto {
    private String city;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private int person;

    @Builder
    public SearchDto(String city, String checkIn, String checkOut, int person) {
        this.city = city;
        this.checkIn = LocalDateTime.parse(checkIn, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.checkOut = LocalDateTime.parse(checkOut, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//        this.checkOut = LocalDateTime.parse(checkOut, formatter).withHour(11).withMinute(0).withSecond(0);
        this.person = person;
    }
}
