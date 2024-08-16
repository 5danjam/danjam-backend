package com.danjam.search.querydsl;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class RoomDto {
    private Long id;
    private int person;
    private int price;

    public RoomDto(Long id, int person, int price) {
        this.id = id;
        this.person = person;
        this.price = price;
    }
}