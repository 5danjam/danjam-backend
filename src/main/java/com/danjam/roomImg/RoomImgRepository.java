package com.danjam.roomImg;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoomImgRepository extends JpaRepository<RoomImg,Long> {

    @Override
    Optional<RoomImg> findById(Long id);

    @Query("SELECT CONCAT(ri.name, '.', ri.ext) as name FROM RoomImg ri WHERE ri.room.dorm.id = :dormId")
    List<String> findRoomImgNamesByDormId(@Param("dormId") Long dormId);
}
