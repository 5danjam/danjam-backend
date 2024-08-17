package com.danjam.search.querydsl;

import com.danjam.room.Room;
import com.danjam.roomImg.RoomImg;
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

    //    public RoomDto(Long id, int person, int price, ImgDto images) {
    public RoomDto(Long id, int person, int price, List<ImgDto> images) {
        this.id = id;
        this.person = person;
        this.price = price;
        this.images = images;
    }
}
