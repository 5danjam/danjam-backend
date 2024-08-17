package com.danjam.room;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// 송준한 룸레파지토리에 룸레파지토리 커스텀 상속이 필요해서 넣었습니다.
public interface RoomRepository extends JpaRepository<Room,Long>, RoomRepositoryCustom {

    @Override
    Optional<Room> findById(Long id);

    List<Room> findByDormId(Long dormId);

}
