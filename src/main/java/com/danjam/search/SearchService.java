package com.danjam.search;

import com.danjam.amenity.AmenityListDTO;
import com.danjam.room.RoomDetailDTO;
import com.danjam.search.querydsl.DormDto;
import com.danjam.search.querydsl.FilterDto;
import com.danjam.search.querydsl.RoomDto;
import com.danjam.search.querydsl.SearchRepo;
import com.querydsl.core.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepo searchRepo;

    public List<DormDto> findList(SearchDto searchDto) {
        return searchRepo.findList(searchDto);
    }

    public Page<DormDto> findAllList(Pageable pageable) {
        return searchRepo.findAllList(pageable);
    }


    public List<String> findByCity(String city) {
        return searchRepo.findByCity(city);
    }

    public List<DormDto> findByFilter(FilterDto filterDto) {
        return searchRepo.findByFilter(filterDto);
    }

    public List<RoomDetailDTO> findAllRoom(SearchDto searchDto, Long dormId) {
        return searchRepo.findAllRoom(searchDto, dormId);
    }

    public List<AmenityListDTO> findAmenity(Long dormId) {
        return searchRepo.findAmenity(dormId);
    }

}