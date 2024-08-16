package com.danjam.search.querydsl;

import com.danjam.search.SearchDto;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FilterDto {
    private SearchDto searchDto;
    private List<Long> amenities;
    private List<String> cities;

    @Builder
    public FilterDto(SearchDto searchDto, List<Long> amenities, List<String> cities) {
        this.searchDto = searchDto;
        this.amenities = amenities;
        this.cities = cities;
    }
}
