package com.danjam.room;

import com.danjam.search.querydsl.ImgDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
// Jackson 어떠한 데이터를 직렬화 할 때 json으로 변경시에 몇몇 값에 널이 있고,
// 나는 널로 들어오는 값들은 보고 싶지 않다면 이 어노테이션으로 조절할 수 있다고 함. https://velog.io/@seulpace/JsonInclude-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RoomDetailDTO {

    private Long id;
    private String name;
    private String description;
    private int person;
    private int price;
    private String type;
    private List<ImgDto> images;

    public RoomDetailDTO(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.description = room.getDescription();
        this.person = room.getPerson();
        this.price = room.getPrice();
        this.type = room.getType();
        this.images = new ArrayList<>();
    }
}