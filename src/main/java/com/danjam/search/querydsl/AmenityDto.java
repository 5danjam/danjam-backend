package com.danjam.search.querydsl;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AmenityDto {
    private Long id;
    private String name;

    @Builder
    public AmenityDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
