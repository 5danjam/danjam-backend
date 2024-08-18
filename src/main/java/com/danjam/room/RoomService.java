package com.danjam.room;

import java.util.List;
import java.util.Optional;

public interface RoomService {

    List<Long> getRoomIdsByDormId(Long dormId);

    Optional<Long> findRoomIdByDormId(Long dormId);

    // 돔에 속한 객체에 룸 필드 다 갖고 오는거
    List<RoomDetailDTO> getRoomByDormId(Long dormId);
}
