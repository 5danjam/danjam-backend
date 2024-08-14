package com.danjam.dorm;

import com.danjam.room.RoomDTO;
import com.danjam.roomImg.RoomImg;
import com.danjam.roomImg.RoomImgDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class DormDTO {

    private Long dormId;
    private String dormName;
    private String dormDescription;
    private String city;
    private String town;
    private Double averageRating;
    private int lowestRoomPrice;
    private List<RoomDTO> rooms;


    public DormDTO(Dorm dorm, Double averageRating, Integer lowestRoomPrice, List<RoomDTO> rooms) {
        this.dormId = dorm.getId();
        this.dormName = dorm.getName();
        this.dormDescription = dorm.getDescription();
        this.city = dorm.getCity();
        this.town = dorm.getTown();
        this.averageRating = (averageRating != null) ? averageRating : 0;
        this.lowestRoomPrice = (lowestRoomPrice != null) ? lowestRoomPrice : 0;
        this.rooms = rooms;

    }


}
