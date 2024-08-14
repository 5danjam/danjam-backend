package com.danjam.roomImg;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoomImgRepository extends JpaRepository<RoomImg,Long> {

    @Override
    Optional<RoomImg> findById(Long id);
}
