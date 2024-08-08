package com.danjam.room;

import com.danjam.dorm.DormDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    // 특정 기숙사에 속한 방들을 가격 내림차순으로 정렬하여 찾는 메서드
    List<Room> findByDormOrderByPriceDesc(DormDTO dormDTO);

}
