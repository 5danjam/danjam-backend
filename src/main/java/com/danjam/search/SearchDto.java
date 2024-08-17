package com.danjam.search;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Data
@NoArgsConstructor
public class SearchDto {
    private String city;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime checkIn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime checkOut;
    private int person;

    @Builder
    public SearchDto(String city, LocalDateTime checkIn, LocalDateTime checkOut, int person) {
        this.city = city;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
//        this.checkIn = LocalDateTime.parse(checkIn).withHour(15).withMinute(0).withSecond(0).withNano(0);
//        this.checkOut = LocalDateTime.parse(checkOut).withHour(11).withMinute(0).withSecond(0).withNano(0);
//        this.checkIn = checkIn.withHour(15).withMinute(0).withSecond(0).withNano(0);
//        this.checkOut = checkOut.withHour(11).withMinute(0).withSecond(0).withNano(0);

//        this.checkIn = new java.sql.Timestamp(checkIn.getTime()).toLocalDateTime();
//        this.checkOut = new java.sql.Timestamp(checkOut.getTime()).toLocalDateTime();

//        this.checkIn = LocalDateTime.parse(checkIn.formatted("yyyy-MM-dd HH:mm:ss")).withHour(15).withMinute(0).withSecond(0);
//        this.checkOut = LocalDateTime.parse(checkOut.formatted("yyyy-MM-dd HH:mm:ss")).withHour(11).withMinute(0).withSecond(0);
//        this.checkOut = LocalDateTime.parse(checkOut, formatter).withHour(11).withMinute(0).withSecond(0);
        this.person = person;
    }
}
