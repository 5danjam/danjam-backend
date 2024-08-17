package com.danjam.dorm.querydsl;

import com.danjam.booking.QBooking;
import com.danjam.booking.querydsl.DormBookingDTO;
import com.danjam.dorm.QDorm;
import com.danjam.room.QRoom;
import com.danjam.room.RoomDTO;
import com.danjam.users.QUsers;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DormRepositoryCustomImpl implements DormRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public List<DormBookingListDTO> findBookingsBySellerId(Long userId) {
        QDorm dorm = QDorm.dorm;
        QRoom room = QRoom.room;
        QBooking booking = QBooking.booking;
        QUsers user = QUsers.users;

        return queryFactory
                .select(Projections.constructor(DormBookingListDTO.class,
                        dorm.id,
                        dorm.name,
                        dorm.address,
                        dorm.status,
                        Projections.constructor(RoomDTO.class,
                                room.id,
                                room.name,
                                room.type),
                        Projections.constructor(DormBookingDTO.class,
                                booking.id,
                                booking.checkIn,
                                booking.checkOut,
                                user.name)))
                .from(dorm)
                .join(dorm.rooms, room)
                .join(room.bookings, booking)
                .join(booking.users, user)
                .where(dorm.user.id.eq(userId))
                .fetch();
    }
}