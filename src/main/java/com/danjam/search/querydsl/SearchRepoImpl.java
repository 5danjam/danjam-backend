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

    @Override
    public List<DormDto> cheapRoom(SearchDto searchDto) {
        String city = searchDto.getCity();
        String checkIn = searchDto.getCheckIn().toString();
        String checkOut = searchDto.getCheckOut().toString();
//        LocalDateTime checkIn = searchDto.getCheckIn();
//        LocalDateTime checkOut = searchDto.getCheckOut();
        LocalDate checkInDate = LocalDate.parse(checkIn);
        LocalDate checkOutDate = LocalDate.parse(checkOut);
        int person = searchDto.getPerson();

        QRoom subRoom = new QRoom("subRoom");
        QBooking subBooking = new QBooking("subBooking");
        JPQLQuery<Integer> groupByDorm = JPAExpressions
                .select(subRoom.price.min())
                .from(subRoom)
                .where(subRoom.dorm.id.eq(qDorm.id))
                .groupBy(subRoom.dorm.id);

        JPQLQuery<LocalDate> groupByCheckIn = JPAExpressions
                .select(qBooking.checkIn)
                .from(qBooking)
                .groupBy(qBooking.room.id);
//                .where(qBooking.room.id.eq(qRoom.id))
//                .groupBy(qBooking.room.id)
//              .fetch().stream().forEach(System.out::println);

        JPQLQuery<LocalDate> groupByCheckOut = JPAExpressions
                .select(subBooking.checkOut)
                .from(subBooking)
                .where(subBooking.room.id.eq(qRoom.id))
                .groupBy(subBooking.room.id);

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
                .where(
//                        qRoom.id.eq(groupByCheckIn.select(qBooking.room.id).where())
                        // 아래 낫비트윈 사용할 때, LocalDate 사용했음->오류 안남
//                        qBooking.checkIn.notBetween(checkIn, checkOut),
//                        qBooking.checkOut.notBetween(checkIn, checkOut),
                        qRoom.price.eq(groupByDorm),
                        qDorm.city.eq(city),
                        qRoom.person.eq(person))
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
