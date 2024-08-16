package com.danjam.search.querydsl;

import com.danjam.search.SearchDto;

import java.util.List;

public interface SearchRepo {
    List<DormDto> findList(SearchDto searchDto);

    List<DormDto> findAllList();

    List<String> findByCity(String city);

    List<DormDto> findByFilter(FilterDto filterDto);
}