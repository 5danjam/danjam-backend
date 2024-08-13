package com.danjam.search.querydsl;

import java.time.LocalDate;
import java.util.List;

public interface SearchRepo {
    List<DormDto> cheapRoom(String city, LocalDate checkIn, LocalDate checkOut, int person);

    List<DormDto> findList();

    List<String> findByCity(String city);

//    List<DormDto> findByDate(String checkIn, String checkOut);

    List<DormDto> findByPerson(String city, int person);
}
