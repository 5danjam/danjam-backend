package com.danjam.search.querydsl;

import java.util.List;

public interface SearchRepo {
    List<DormDto> cheapRoom(String city, int person);

    List<DormDto> findList();

    List<String> findByCity(String city);

//    List<DormDto> findByDate(String checkIn, String checkOut);

    List<DormDto> findByPerson(String city, int person);
}
