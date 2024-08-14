package com.danjam.roomImg;

public class RoomImgDTO {

    private final String name;
    private final String url;

    public RoomImgDTO(RoomImg roomImg) {
        this.name = roomImg.getName();
        this.url = "/images/" + roomImg.getName() + "." + roomImg.getExt();
    }

}
