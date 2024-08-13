package com.danjam.search.querydsl;

import com.danjam.booking.QBooking;
import com.danjam.d_category.QDcategory;
import com.danjam.dorm.QDorm;
import com.danjam.room.QRoom;
import com.danjam.room.RoomController;
import com.danjam.users.QUsers;
import com.querydsl.core.Tuple;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import static com.querydsl.core.types.Projections.list;

@Repository
@RequiredArgsConstructor
public class SearchRepoImpl implements SearchRepo {
    private final JPAQueryFactory queryFactory;
    private final QDorm qDorm = QDorm.dorm;
    private final QUsers qUsers = QUsers.users;
    private final QDcategory qDcategory = QDcategory.dcategory;
    private final QRoom qRoom = QRoom.room;
    private final QBooking qBooking = QBooking.booking;
    private final RoomController roomController;

    @Override
    public List<DormDto> cheapRoom(String city, LocalDate checkIn, LocalDate checkOut, int person) {
        JPQLQuery<Tuple> minPrice = JPAExpressions
                .select(qRoom.dorm.id, qRoom.price)
                .from(qRoom)
                .groupBy(qRoom.dorm.id);

        return queryFactory
                .select(Projections.constructor(DormDto.class,
                        qDorm.id,
                        qDorm.name,
                        qDorm.description,
                        qDorm.contactNum,
                        qDorm.city,
                        qDorm.town,
                        qDorm.address,
                        Projections.constructor(CategoryDto.class,
                                qDcategory.id,
                                qDcategory.name),
                        Projections.constructor(UserDto.class,
                                qUsers.id,
                                qUsers.name,
                                qUsers.role),
                        Projections.constructor(RoomDto.class,
                                qRoom.id,
                                qRoom.person,
                                qRoom.price)
//                        queryFactory.select(Projections.constructor(RoomDto.class,
//                                qRoom.id,
//                                qRoom.person,
//                                qRoom.price))
//                                .from(qRoom).where(minPrice.having(qRoom.price.eq(qRoom.price.min()))).groupBy(qRoom.dorm.id)
//                                Projections.constructor(CategoryDto.class,
//                                qDcategory.id,
//                                qDcategory.name),
//                        Projections.constructor(UserDto.class,
//                                qUsers.id,
//                                qUsers.name,
//                                qUsers.role),
//                        list(Projections.constructor(RoomDto.class,
//                                qRoom.id,
//                                qRoom.person,
//                                qRoom.price,
//                                list(Projections.constructor(BookingDto.class,
//                                        qBooking.id,
//                                        qBooking.checkIn,
//                                        qBooking.checkOut
//                                ))))
                ))
                .from(qDorm)
                .join(qDorm.dcategory, qDcategory)
                .join(qDorm.user, qUsers)
                //.join(qRoom).on(qDorm.id.eq(qRoom.dorm.id))

//                .join(qBooking.booking, qBooking)

                .where(qDorm.city.eq(city),
                        qRoom.price.eq(minPrice.select(qRoom.price)),
//                        qBooking.checkIn.notBetween(checkIn, checkOut),
//                        qBooking.checkOut.notBetween(checkIn, checkOut),
                        qRoom.person.eq(person))
                .fetch();

        // Subquery to find available rooms
        // chatGPT
        /*JPAQuery<Long> availableRoomsSubquery = queryFactory
                .select(qRoom.id)
                .from(qRoom)
                .leftJoin(qBooking)
                .on(qRoom.id.eq(qBooking.room.id))
                .where(
                        qBooking.room.id.isNull()
                                .or(
                                        qBooking.checkIn.loe(checkOut)
                                                .and(qBooking.checkOut.goe(checkIn))
                                )
                );

        // Subquery to find cheapest rooms per dorm
        new JPA<
        JPAQuery<Tuple> cheapestRoomsQuery = queryFactory
                .select(qRoom.dorm.id, qRoom.price.min())
                .from(qRoom)
                .where(qRoom.id.in(availableRoomsSubquery))
                .groupBy(qRoom.dorm.id);

        // Main query to find dorms with the cheapest available rooms
        return queryFactory
                .select(Projections.constructor(DormDto.class,
                        qDorm.id,
                        qDorm.name,
                        qDorm.description,
                        qDorm.contactNum,
                        qDorm.city,
                        qDorm.town,
                        qDorm.address,
                        Projections.constructor(CategoryDto.class,
                                qDcategory.id,
                                qDcategory.name
                        ),
                        Projections.constructor(UserDto.class,
                                qUsers.id,
                                qUsers.name,
                                qUsers.role
                        ),
                        Projections.list(Projections.constructor(RoomDto.class,
                                qRoom.id,
                                qRoom.person,
                                qRoom.price
                        ))
                ))
                .from(qDorm)
                .join(qDorm.dcategory, qDcategory)
                .join(qDorm.user, qUsers)
                .leftJoin(qRoom).on(qDorm.id.eq(qRoom.dorm.id))
                .where(qRoom.id.in(
                        JPAExpressions
                                .select(qRoom.id)
                                .from(qRoom)
                                .leftJoin(qBooking)
                                .on(qRoom.id.eq(qBooking.room.id))
                                .where(
                                        qBooking.room.id.isNull()
                                                .or(
                                                        qBooking.checkIn.loe(checkOut)
                                                                .and(qBooking.checkOut.goe(checkIn))
                                                )
                                )
                ))
                .groupBy(
                        qDorm.id,
                        qDorm.name,
                        qDorm.description,
                        qDorm.contactNum,
                        qDorm.city,
                        qDorm.town,
                        qDorm.address,
                        qDcategory.id,
                        qDcategory.name,
                        qUsers.id,
                        qUsers.name,
                        qUsers.role
                )
                .fetch();
        
        */
        /*// Subquery to find available rooms
        JPAQuery<Long> availableRoomsSubquery = queryFactory.select(qRoom.id)
                .from(qRoom)
                .leftJoin(QBooking.booking)
                .on(qRoom.id.eq(QBooking.booking.room.id))
                .where(
                        QBooking.booking.room.id.isNull()
                                .or(
                                        QBooking.booking.checkIn.loe(checkOut)
                                                .and(QBooking.booking.checkOut.goe(checkIn))
                                )
                );

        // Subquery to find cheapest rooms per dorm
        JPAQuery<Tuple> cheapestRoomsQuery = queryFactory
                .select(qRoom.dorm.id, qRoom.price.min())
                .from(qRoom)
                .where(qRoom.id.in(availableRoomsSubquery))
                .groupBy(qRoom.dorm.id);

        // Main query to find dorms with the cheapest available rooms
        return queryFactory
                .select(Projections.constructor(DormDto.class,
                        qDorm.id,
                        qDorm.name,
                        qDorm.description,
                        qDorm.contactNum,
                        qDorm.city,
                        qDorm.town,
                        qDorm.address,
                        Projections.constructor(CategoryDto.class,
                                qDcategory.id,
                                qDcategory.name
                        ),
                        Projections.constructor(UserDto.class,
                                qUserss.id,
                                qUserss.name,
                                qUserss.role
                        ),
                        Projections.list(Projections.constructor(RoomDto.class,
                                qRoom.id,
                                qRoom.person,
                                qRoom.price
                        ))
                ))
                .from(qDorm)
                .join(qDorm.dcategory, qDcategory)
                .join(qDorm.user, qUserss)
                .leftJoin(qRoom).on(qDorm.id.eq(qRoom.dorm.id))
                .where(qDorm.city.eq(city),
                        qRoom.id.in(
                                JPAExpressions
                                        .select(qRoom.id)
                                        .from(qRoom)
                                        .leftJoin(QBooking.booking)
                                        .on(qRoom.id.eq(QBooking.booking.room.id))
                                        .where(
                                                QBooking.booking.room.id.isNull()
                                                        .or(
                                                                QBooking.booking.checkIn.loe(checkOut)
                                                                        .and(QBooking.booking.checkOut.goe(checkIn))
                                                        )
                                        )
                        ),
                        qRoom.person.eq(person))
                .groupBy(qDorm.id)
                .fetch();*/
    }

    @Override
    public List<DormDto> findList() {
        QRoom subRoom = new QRoom("subRoom");

        JPQLQuery<Integer> groupByDorm = JPAExpressions
                .select(subRoom.price.min())
                .from(subRoom)
                .where(subRoom.dorm.id.eq(qDorm.id))
                .groupBy(subRoom.dorm.id);

        return queryFactory
                .select(Projections.constructor(DormDto.class,
                        qDorm.id,
                        qDorm.name,
                        qDorm.description,
                        qDorm.contactNum,
                        qDorm.city,
                        qDorm.town,
                        qDorm.address,
                        Projections.constructor(CategoryDto.class,
                                qDcategory.id,
                                qDcategory.name),
                        Projections.constructor(UserDto.class,
                                qUsers.id,
                                qUsers.name,
                                qUsers.role),
                        Projections.constructor(RoomDto.class,
                                qRoom.id,
                                qRoom.person,
                                qRoom.price)
                ))
                .from(qDorm)
                .join(qDorm.dcategory, qDcategory)
                .join(qDorm.user, qUsers)
                .join(qRoom).on(qDorm.id.eq(qRoom.dorm.id))
                .where(qRoom.price.eq(groupByDorm),
                        )
                .fetch();
    }

    @Override
    public List<String> findByCity(String city) {
        return queryFactory
                .select(qDorm.dorm.town)
                .from(qDorm)
                .where(qDorm.city.eq(city))
                .distinct()
                .fetch();
    }

    @Override
    public List<DormDto> findByPerson(String city, int person) {
        return queryFactory
                .select(Projections.constructor(DormDto.class,
                        qDorm.id,
                        qDorm.name,
                        qDorm.description,
                        qDorm.contactNum,
                        qDorm.city,
                        qDorm.town,
                        qDorm.address,
                        Projections.constructor(CategoryDto.class,
                                qDcategory.id,
                                qDcategory.name),
                        Projections.constructor(UserDto.class,
                                qUsers.id,
                                qUsers.name,
                                qUsers.role
                        )
                        /*,
                        Projections.constructor(RoomDto.class,
                                room.id,
                                room.person,
                                room.price,
                                room.qDorm.id
                        )*/
                ))
                .from(qDorm)
                .join(qDorm.dcategory, qDcategory)
                .join(qDorm.user, qUsers)
//                .join(room.qDorm, qDorm)
                .where(qDorm.city.eq(city)
                        /*,room.qDorm.id.eq(qDorm.id),
                        room.person.eq(person)*/
                )
                .fetch();
    }
}
