package com.danjam.search.querydsl;

import com.danjam.search.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchRepo {
    List<DormDto> findList(SearchDto searchDto);

    List<DormDto> findAllList();

    List<String> findByCity(String city);

    Page<DormDto> findByFilter(FilterDto filterDto, Pageable pageable);
}