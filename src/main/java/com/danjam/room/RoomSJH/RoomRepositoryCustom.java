package com.danjam.room;

import java.util.List;
import java.util.Optional;

public interface RoomRepositoryCustom {
    Optional<Long> findRoomIdsByDormId(Long dormId);

    List<Room> getRoomByDormId(Long dormId);
}
