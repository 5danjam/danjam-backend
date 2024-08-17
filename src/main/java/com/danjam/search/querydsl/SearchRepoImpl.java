package com.danjam.search.querydsl;

import com.danjam.amenity.QAmenity;
import com.danjam.booking.QBooking;
import com.danjam.d_amenity.QDamenity;
import com.danjam.d_category.QDcategory;
import com.danjam.dorm.QDorm;
import com.danjam.review.QReview;
import com.danjam.room.QRoom;
import com.danjam.search.SearchDto;
import com.danjam.users.QUsers;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SearchRepoImpl implements SearchRepo {
    private final JPAQueryFactory queryFactory;
    private final QDorm qDorm = QDorm.dorm;
    private final QUsers qUsers = QUsers.users;
    private final QDcategory qDcategory = QDcategory.dcategory;
    private final QRoom qRoom = QRoom.room;
    private final QDamenity qDamenity = QDamenity.damenity;
    private final QReview qReview = QReview.review;
    /*private final QRoom subRoom = new QRoom("subRoom");

    private final JPQLQuery<Integer> groupByDorm = JPAExpressions
            .select(subRoom.price.min())
            .from(subRoom)
            .where(subRoom.dorm.id.eq(qDorm.id))
            .groupBy(subRoom.dorm.id);

    private JPQLQuery<DormDto> dormList = queryFactory
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
            .join(qRoom).on(qDorm.id.eq(qRoom.dorm.id));*/

    @Override
    public List<DormDto> findAllList() {
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

    // filter를 위한 townList
    @Override
    public List<String> findByCity(String city) {
        if (city == null || city.isEmpty()) {
            return null;
        }
        return queryFactory
                .select(qDorm.dorm.town)
                .from(qDorm)
                .where(qDorm.city.eq(city))
                .distinct()
                .fetch();
    }

    @Override
    public List<DormDto> findList(SearchDto searchDto) {
        System.out.println("findList");
        String city = searchDto.getCity();
        LocalDateTime checkIn = searchDto.getCheckIn();
        LocalDateTime checkOut = searchDto.getCheckOut();
        int person = searchDto.getPerson();
        System.out.println(searchDto);

        QRoom subRoom = new QRoom("subRoom");
        QBooking subBooking = new QBooking("subBooking");

        JPQLQuery<Long> groupByDate = JPAExpressions
                .select(subBooking.room.id)
                .from(subBooking)
                .where(subBooking.checkIn.between(checkIn, checkOut),
                        subBooking.checkOut.between(checkIn, checkOut));

        JPQLQuery<Integer> groupByDorm = JPAExpressions
                .select(subRoom.price.min())
                .from(subRoom)
                .where(subRoom.dorm.id.eq(qDorm.id))
                .groupBy(subRoom.dorm.id);

        // 검색 조건에 따른 필터 만들기
        BooleanExpression groupBySearch;
        if (searchDto.getCity().equalsIgnoreCase("선택")) { // 도시를 선택하지 않았을 경우
            groupBySearch = qRoom.id.notIn(groupByDate)
                    .and(qRoom.price.eq(groupByDorm))
                    .and(qRoom.person.goe(person))
                    .and(qDorm.id.eq(qRoom.dorm.id));
        } else {
            groupBySearch = qRoom.id.notIn(groupByDate)
                    .and(qRoom.price.eq(groupByDorm))
                    .and(qRoom.person.goe(person)) // 방의 수용인원이 person보다 크거나 같다면 보여줌
                    .and(qDorm.city.eq(city));
        }

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
                        // review rate
                        /*Projections.constructor(ReivewDto.class,
                                qReview.id,
                                qReview.rate,
                                qReview.booking)*/
                ))
                .from(qDorm)
                .join(qDorm.dcategory, qDcategory)
                .join(qDorm.user, qUsers)
                .join(qRoom).on(qDorm.id.eq(qRoom.dorm.id))
                .where(
                        groupBySearch
                )
                .fetch();
    }

    @Override
    public List<DormDto> findByFilter(FilterDto filterDto) {
        System.out.println("findByAmenity");
        System.out.println(filterDto.getSearchDto());
        System.out.println(filterDto.getAmenities());
        System.out.println(filterDto.getCities());

        LocalDateTime checkIn = filterDto.getSearchDto().getCheckIn();
        LocalDateTime checkOut = filterDto.getSearchDto().getCheckOut();
        int person = filterDto.getSearchDto().getPerson();

        QDorm subDorm = new QDorm("subDorm");
        QRoom subRoom = new QRoom("subRoom");
        QBooking subBooking = new QBooking("subBooking");
        QAmenity subAmenity = new QAmenity("subAmenity");
        QDamenity subDamenity = new QDamenity("subDamenity");

        // checkIn, checkOut 날짜에 예약 없는 방 보여주기
        JPQLQuery<Long> groupByDate = JPAExpressions
                .select(subBooking.room.id)
                .from(subBooking)
                .where(subBooking.checkIn.between(checkIn, checkOut),
                        subBooking.checkOut.between(checkIn, checkOut));

        // 가장 저렴한 가격 가진 방 보여주기
        JPQLQuery<Integer> groupByDorm = JPAExpressions
                .select(subRoom.price.min())
                .from(subRoom)
                .where(subRoom.dorm.id.eq(qDorm.id))
                .groupBy(subRoom.dorm.id);

        // 검색 조건에 따른 필터 만들기
        BooleanExpression groupBySearch;
        if (filterDto.getSearchDto().getCity().equalsIgnoreCase("선택")) { // 도시를 선택하지 않았을 경우
            groupBySearch = qRoom.id.notIn(groupByDate)
                    .and(qRoom.price.eq(groupByDorm))
                    .and(qRoom.person.goe(person))
                    .and(qDorm.id.eq(qRoom.dorm.id));
        } else {
            groupBySearch = qRoom.id.notIn(groupByDate)
                    .and(qRoom.price.eq(groupByDorm))
                    .and(qRoom.person.goe(person)) // 방의 수용인원이 person보다 크거나 같다면 보여줌
                    .and(qDorm.city.eq(filterDto.getSearchDto().getCity()));
        }

        // filterDto에서 골라준 town과 같은 town의 호텔 반환
        JPQLQuery<Long> groupByTown = JPAExpressions
                .select(subDorm.id)
                .from(subDorm)
                .where(subDorm.town.in(filterDto.getCities()));

        // filterDto에서 선택한 amenity에 맞는 dorm 반환
        JPQLQuery<Long> groupByDamenity = JPAExpressions
                .select(subDamenity.dorm.id)
                .from(subDamenity)
                .where(subDamenity.amenity.id.in(filterDto.getAmenities()))
                .groupBy(subDamenity.dorm.id)
                .having(subDamenity.amenity.id.countDistinct().eq((long) filterDto.getAmenities().size()));

        // amentiy & town filter 리스트
        JPQLQuery<Long> groupByFilter;

        if (filterDto.getCities().isEmpty() && !filterDto.getAmenities().isEmpty()) {
            groupByFilter = JPAExpressions
                    .select(subDamenity.dorm.id)
                    .from(subDamenity)
                    .where(subDamenity.amenity.id.in(filterDto.getAmenities()))
                    .groupBy(subDamenity.dorm.id)
                    .having(subDamenity.amenity.id.countDistinct().eq((long) filterDto.getAmenities().size()));
        } else if (!filterDto.getCities().isEmpty() && filterDto.getAmenities().isEmpty()) {
            groupByFilter = JPAExpressions
                    .select(subDorm.id)
                    .from(subDorm)
                    .where(subDorm.town.in(filterDto.getCities()));
        } else {
            groupByFilter = JPAExpressions
                    .select(qDamenity.dorm.id)
                    .from(qDamenity)
                    .join(qDorm).on(qDamenity.dorm.id.eq(qDorm.id))
                    .where(qDorm.id.in(groupByTown).and(qDorm.id.in(groupByDamenity)));
        }


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
                .join(qRoom).on(qDorm.id.eq(qRoom.dorm.id))
                .where(
                        groupBySearch,
                        qDorm.id.in(groupByFilter)
                )
                .fetch();
    }
}
