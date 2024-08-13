package com.danjam.search.querydsl;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private RoomDto rooms;
//    private List<RoomDto> rooms;
//    private List<BookingDto> bookings;

//    public DormDto(Long id, String name, String description, String contactNum, String city, String town, String address, CategoryDto dcategory, UserDto user, List<RoomDto> rooms, List<BookingDto> bookings) {
    public DormDto(Long id, String name, String description, String contactNum, String city, String town, String address, CategoryDto dcategory, UserDto user, RoomDto rooms) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.contactNum = contactNum;
        this.city = city;
        this.town = town;
        this.address = address;
        this.dcategory = dcategory;
        this.user = user;
//        this.room = room;
        this.rooms = rooms;
//        this.bookings = bookings;
    }
}
