package com.danjam.dorm.querydsl;

import com.danjam.booking.querydsl.DormBookingDTO;
import com.danjam.booking.QBooking;
import com.danjam.dorm.QDorm;
import com.danjam.room.QRoom;
import com.danjam.room.RoomDTO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.danjam.d_category.Dcategory;
import com.danjam.d_category.DcategoryListDTO;
import com.danjam.d_category.QDcategory;
import com.danjam.dorm.Dorm;
import com.danjam.dorm.QDorm;
import com.danjam.users.QUsers;
import com.danjam.users.Users;
import com.danjam.users.UsersDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
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

   @Override
    public List<DormDto> findList() {
        QDorm dorm = QDorm.dorm;
        QDcategory dcategory = QDcategory.dcategory;
        QUsers users = QUsers.users;


        return queryFactory
                .select(Projections.constructor(DormDto.class,
                        dorm.id,
                        dorm.name,
                        dorm.address,
                        Projections.constructor(CategoryDto.class,
                                dcategory.id,
                                dcategory.name),
                        Projections.constructor(UserDto.class,
                                users.id,
                                users.name,
                                users.role
                                )))
                .from(dorm)
                .join(dorm.dcategory, dcategory)
                .join(dorm.user, users)
                .fetch();
    }
}
