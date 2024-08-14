package com.danjam.search.querydsl;

import com.danjam.search.SearchDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.logging.Filter;

@Data
@NoArgsConstructor
public class FilterDto {
    private SearchDto searchDto;
    private List<AmenityDto> amenities;
    private List<String> cities;

    @Builder
    public FilterDto(SearchDto searchDto, List<AmenityDto> amenities, List<String> cities) {
        this.searchDto = searchDto;
        this.amenities = amenities;
        this.cities = cities;
    }
}
