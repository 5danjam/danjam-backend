package com.danjam.room;

import java.util.List;
import java.util.Optional;

public interface RoomRepositoryCustom {
    //todo 송준한 0818 0502
    // 특정 조건 만족하면 룸 객체 반환 하는 커스텀 메서드
    List<Room> findRoomByCustomCriteria(int person, String type);


    Optional<Long> findRoomIdsByDormId(Long dormId);

}
