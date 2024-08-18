package com.danjam.search.querydsl;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
public class DormDto {
    private Long id;
    private String name;
    private String description;
    private String contactNum;
    private String city;
    private String town;
    private String address;
    private CategoryDto dcategory; //카테고리
    private UserDto user;
    private RoomDto room;
    private Double rate;

    public DormDto(Long id, String name, String description, String contactNum, String city, String town, String address,
                   CategoryDto dcategory, UserDto user, RoomDto room, Double rate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.contactNum = contactNum;
        this.city = city;
        this.town = town;
        this.address = address;
        this.dcategory = dcategory;
        this.user = user;
        this.room = room;
        this.rate = rate;
    }

    public <T, E> DormDto(Long id, Object o, Object o1, Object o2, Object o3, Object o4, Object o5,
                          CategoryDto categoryDto, UserDto userDto, ArrayList<E> es) {
        this.id = id;
        this.name = (String) o;
        this.description = (String) o1;
        this.contactNum = (String) o2;
        this.city = (String) o3;
        this.town = (String) o4;
        this.address = (String) o5;
        this.dcategory = (CategoryDto) o5;
        this.user = (UserDto) o5;
        this.room = (RoomDto) o5;
        this.rate = (Double) o5;
    }
}
