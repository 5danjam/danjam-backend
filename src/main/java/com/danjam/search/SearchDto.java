package com.danjam.search;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SearchDto {
    private String city;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int person;

    @Builder
    public SearchDto(String city, LocalDate checkIn, LocalDate checkOut, int person) {
        this.city = city;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.person = person;
    }
}
