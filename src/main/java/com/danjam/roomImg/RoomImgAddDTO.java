package com.danjam.roomImg;

import com.danjam.room.Room;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomImgAddDTO {

    private String name;

    private String nameOriginal;

    private String size;

    private String ext;

    private Room room;

    private Long roomId;

    public RoomImg toEntity(){
        return RoomImg.builder()
                .name(name)
                .nameOriginal(nameOriginal)
                .size(size)
                .ext(ext)
                .room(room)
                .build();
    }

}
