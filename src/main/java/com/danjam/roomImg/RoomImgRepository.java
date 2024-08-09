package com.danjam.roomImg;

import com.danjam.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoomImgRepository extends JpaRepository<RoomImg,Long> {

    @Override
    Optional<RoomImg> findById(Long id);

    List<RoomImg> findByRoom(Room room);
}
