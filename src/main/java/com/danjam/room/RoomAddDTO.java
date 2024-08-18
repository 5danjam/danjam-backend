package com.danjam.room;

import com.danjam.dorm.Dorm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomAddDTO {

    private String name;

    private String description;

    private int person;

    private int price;

    private String type;

    private Dorm dorm;

    private Long dormId;

    public Room toEntity() {
        return Room.builder()
                .name(name)
                .description(description)
                .person(person)
                .price(price)
                .type(type)
                .dorm(dorm)
                .build();
    }

}
