package com.danjam.search.querydsl;

import com.danjam.booking.QBooking;
import com.danjam.d_category.QDcategory;
import com.danjam.dorm.QDorm;
import com.danjam.room.QRoom;
import com.danjam.room.RoomController;
import com.danjam.search.SearchDto;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    @Override
    public List<DormDto> cheapRoom(SearchDto searchDto) {
        System.out.println("cheapRoom");
        String city = searchDto.getCity();
        LocalDateTime checkIn = searchDto.getCheckIn();
        LocalDateTime checkOut = searchDto.getCheckOut();
        int person = searchDto.getPerson();

        QRoom subRoom = new QRoom("subRoom");
        QBooking subBooking = new QBooking("subBooking");

        JPQLQuery<Long> groupByDate = JPAExpressions
                .select(subBooking.room.id)
                .from(subBooking)
                .where(subBooking.checkIn.notBetween(checkIn, checkOut),
                        subBooking.checkOut.notBetween(checkIn, checkOut));

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
                        /*,
                        list(Projections.constructor(BookingDto.class,
                                qBooking.id,
                                qBooking.checkIn,
                                qBooking.checkOut,
                                qBooking.room))*/
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
                .join(qRoom).on(qDorm.id.eq(qRoom.dorm.id))
                .where(
                        qRoom.id.notIn(groupByDate),
                        qRoom.price.eq(groupByDorm),
                        qDorm.city.eq(city),
                        qRoom.person.loe(person))
                .fetch();
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
                .where(qRoom.price.eq(groupByDorm))
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
    public List<DormDto> findByAmenity(SearchDto searchDto, int amenityId) {
        String city = searchDto.getCity();
        LocalDateTime checkIn = searchDto.getCheckIn();
        LocalDateTime checkOut = searchDto.getCheckOut();
        int person = searchDto.getPerson();

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
