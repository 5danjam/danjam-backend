package com.danjam.room;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.danjam.room.QRoom.room;

@Repository
@RequiredArgsConstructor
@Primary
public class RoomRepositoryCustomImpl implements RoomRepositoryCustom {

    private final JPAQueryFactory JPAQUERYFACTORY;


    @Override
    public Optional<Long> findRoomIdsByDormId(Long dormId) {
        Long roomId = JPAQUERYFACTORY
                .select(room.id)
                .from(room)
                .where(room.dorm.id.eq(dormId)) // 쿼리디에스엘을 이용해 참조함.
                .fetchOne();

        return Optional.ofNullable(roomId);
    }

    @Override
    public List<Room> getRoomByDormId(Long dormId) {
        return JPAQUERYFACTORY // 룸 엔티티 전첵(프롬) 선택 돔아이디와 일치하는 룸만 조회를 하고 결과를 리스트로 반환을 함.
                .selectFrom(room)
                .join(room.dorm).fetchJoin() // Dorm을 함께 로드
                .where(room.dorm.id.eq(dormId))
                .fetch();
    }
}

