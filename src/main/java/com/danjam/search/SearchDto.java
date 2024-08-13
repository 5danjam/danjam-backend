package com.danjam.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

@Data
@NoArgsConstructor
public class SearchDto {
    private String city;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int person;

    @Builder
    public SearchDto(String city, String checkIn, String checkOut, int person) {
        this.city = city;
        this.checkIn = LocalDate.parse(checkIn);
        this.checkOut = LocalDate.parse(checkOut);
        this.person = person;
    }
}
