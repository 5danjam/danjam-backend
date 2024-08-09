package com.danjam.room;


import com.danjam.dorm.Dorm;
import com.danjam.roomImg.RoomImg;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room,Long> {

    @Override
    Optional<Room> findById(Long id);

    List<Room> findByDorm(Dorm dorm);

    @Query("SELECT MIN(r.price) FROM Room r WHERE r.dorm.id = :dormId")
    Integer findLowestRoomPriceByDormId(Long dormId);
}
