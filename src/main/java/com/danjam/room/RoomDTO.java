package com.danjam.room;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
// Jackson 어떠한 데이터를 직렬화 할 때 json으로 변경시에 몇몇 값에 널이 있고,
// 나는 널로 들어오는 값들은 보고 싶지 않다면 이 어노테이션으로 조절할 수 있다고 함. https://velog.io/@seulpace/JsonInclude-%EC%96%B4%EB%85%B8%ED%85%8C%EC%9D%B4%EC%85%98


public class RoomDTO {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPerson() {
        return person;
    }

    public void setPerson(int person) {
        this.person = person;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String description;
    private int person;
    private int price;
    private String type;

    public RoomDTO(Room room) {
        this.id = room.getId();
        this.name = room.getName();
        this.description = room.getDescription();
        this.person = room.getPerson();
        this.price = room.getPrice();
        this.type = room.getType();
    }

    @Override
    public String toString() {
        return "RoomDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", person=" + person +
                ", price=" + price +
                ", type='" + type + '\'' +
                '}';
    }
}
