package com.danjam.search.querydsl;

import com.danjam.search.SearchDto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface SearchRepo {
    List<DormDto> cheapRoom(SearchDto searchDto);

    List<DormDto> findList();

    List<String> findByCity(String city);

//    List<DormDto> findByDate(String checkIn, String checkOut);

    List<DormDto> findByAmenity(SearchDto searchDto, int amenityId);
}
