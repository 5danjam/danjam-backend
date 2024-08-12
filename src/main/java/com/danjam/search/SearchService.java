package com.danjam.search;

import com.danjam.search.querydsl.DormDto;
import com.danjam.search.querydsl.SearchRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepo searchRepo;

    public List<DormDto> cheapRoom(String city, int person) {
        return searchRepo.cheapRoom(city, person);
    }

    public List<DormDto> findList() {
        return searchRepo.findList();
    }

    public List<String> findByCity(String city) {
        return searchRepo.findByCity(city);
    }

    public List<DormDto> findByPerson(String city, int person) {
        return searchRepo.findByPerson(city, person);
    }

}
