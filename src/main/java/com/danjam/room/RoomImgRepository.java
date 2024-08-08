package com.danjam.room;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomImgRepository extends JpaRepository<RoomImg, Long> {
    List<RoomImg> findByRoom(Room room);
}
