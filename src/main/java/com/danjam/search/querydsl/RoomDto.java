package com.danjam.search.querydsl;

import com.danjam.dorm.Dorm;
import com.danjam.room.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
