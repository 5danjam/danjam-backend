package com.danjam.room;

import java.util.List;
import java.util.Optional;

public interface RoomService {


    // Room getRoomById(Long id);
    List<Long> getRoomIdsByDormId(Long dormId);

    Optional<Long> findRoomIdByDormId(Long dormId);

    // 돔에 속한 객체에 룸 필드 다 갖고 오는거
    List<RoomDTO> getRoomByDormId(Long dormId);

    List<Room> searchRooms(int person, String type);

}
