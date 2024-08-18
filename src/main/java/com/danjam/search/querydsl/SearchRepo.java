package com.danjam.search.querydsl;

import com.danjam.amenity.AmenityListDTO;
import com.danjam.room.RoomDetailDTO;
import com.danjam.search.SearchDto;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SearchRepo {
    List<DormDto> findList(SearchDto searchDto);

    Page<DormDto> findAllList(Pageable pageable);

    List<String> findByCity(String city);

    List<DormDto> findByFilter(FilterDto filterDto);

    List<RoomDetailDTO> findAllRoom(SearchDto searchDto, Long dormId);

    List<AmenityListDTO> findAmenity(Long dormId);
}
