package com.danjam.search.querydsl;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class RoomDto {
    private Long id;
    private int person;
    private int price;
    private List<ImgDto> images;

    public RoomDto(Long id, int person, int price, List<ImgDto> images) {
        this.id = id;
        this.person = person;
        this.price = price;
        this.images = images;
    }

    public <T, E> RoomDto(Long roomId, Object o, Object o1, Object o2, ArrayList<E> es) {
        this.id = roomId;
        this.person = o1.hashCode();
        this.price = o2.hashCode();
        this.images = new ArrayList<>();
    }
}
