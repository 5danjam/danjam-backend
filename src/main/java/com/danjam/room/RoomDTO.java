package com.danjam.room;

import com.danjam.dorm.Dorm;
import com.danjam.roomImg.RoomImgDTO;
import lombok.Data;

import java.util.List;

@Data
public class RoomDTO {

    private Long roomId;
    private String roomName;
    private String roomDescription;
    private int person;
    private int price;
    private String type;
    private List<RoomImgDTO> roomImages;

    public RoomDTO(Room room, List<RoomImgDTO> roomImages) {
        this.roomId = room.getId();
        this.roomName = room.getName();
        this.roomDescription = room.getDescription();
        this.person = room.getPerson();
        this.price = room.getPrice();
        this.type = room.getType();
        this.roomImages = roomImages;
    }
}
