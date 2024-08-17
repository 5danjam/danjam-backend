package com.danjam.search.querydsl;

import com.danjam.booking.QBooking;
import com.danjam.d_amenity.QDamenity;
import com.danjam.d_category.QDcategory;
import com.danjam.dorm.QDorm;
import com.danjam.review.QReview;
import com.danjam.room.QRoom;
import com.danjam.roomImg.QRoomImg;
import com.danjam.search.SearchDto;
import com.danjam.users.QUsers;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
    private final QDamenity qDamenity = QDamenity.damenity;
    private final QReview qReview = QReview.review;
    private final QRoomImg qRoomImg = QRoomImg.roomImg;
    private final QBooking qBooking = QBooking.booking;

    QDorm subDorm = new QDorm("subDorm");
    QRoom subRoom = new QRoom("subRoom");
    QRoomImg subImg = new QRoomImg("subImg");
    QReview subReview = new QReview("subReview");
    QBooking subBooking = new QBooking("subBooking");
    QDamenity subDamenity = new QDamenity("subDamenity");

    // 서브쿼리
    // 방 최저가 찾기
    JPQLQuery<Integer> groupByDorm = JPAExpressions
            .select(subRoom.price.min())
            .from(subRoom)
            .where(subRoom.dorm.id.eq(qDorm.id))
            .groupBy(subRoom.dorm.id);

    // 호텔 기준으로 리뷰 평점 구하기
    JPQLQuery<Double> groupByReview = JPAExpressions
            .select(subReview.rate.avg())
            .from(subReview)
            .join(subBooking).on(subReview.booking.id.eq(subBooking.id))
            .join(subRoom).on(subBooking.room.id.eq(subRoom.id))
            .where(subRoom.dorm.id.eq(qDorm.id));

    @Override
    public List<DormDto> findAllList() {
        /*List<ImgDto> groupImg = JPAExpressions
                .select(Projections.fields(ImgDto.class,
                        qRoomImg.id,
                        qRoomImg.name,
                        qRoomImg.ext))
                .from(qRoomImg)
                .where(qRoomImg.room.id.eq(qRoom.id)).fetch();
        List<RoomDto> dormList = groupImg.stream()
                .map(r -> new RoomDto(r.))
                .collect(Collectors.toList());*/

        JPQLQuery<Long> groupImg = JPAExpressions
                .select(subImg.id)
                .from(subImg)
                .where(subImg.room.id.eq(qRoom.id))
                .groupBy(subImg.room.id);

        /*JPQLQuery<ImgDto> groupImg = JPAExpressions
                .select(Projections.constructor(ImgDto.class,
                        subImg.id,
                        subImg.name,
                        subImg.ext))
                .from(subImg)
                .where(subImg.room.id.eq(qRoom.id))
                .groupBy(subImg.room.id);*/

        /*JPQLQuery<ImgDto> groupImg = (JPQLQuery<ImgDto>) JPAExpressions
                .select(Projections.constructor(ImgDto.class,
                        qRoomImg.id,
                        qRoomImg.name,
                        qRoomImg.ext))
                .from(qRoomImg)
                .where(qRoomImg.room.id.eq(qRoom.id)).fetchOne();*/

        /*List<ImgDto> groupImg = JPAExpressions
                .select(Projections.constructor(ImgDto.class,
                        qRoomImg.id,
                        qRoomImg.name,
                        qRoomImg.ext))
                .from(qRoomImg)
                .where(qRoomImg.room.id.eq(qRoom.id))
                .fetch();*/

        /*JPQLQuery<List<ImgDto>> groupImg = JPAExpressions
                .select(list(Projections.constructor(ImgDto.class,
                        qRoomImg.id,
                        qRoomImg.name,
                        qRoomImg.ext)))
                .from(qRoomImg)
                .where(qRoomImg.room.id.eq(qRoom.id))
                .groupBy(qRoom.id);*/

        // 영우형 버전
        List<DormDto> dormDtoList = queryFactory
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
                                qRoom.price,
                                list(Projections.constructor(ImgDto.class,
                                        qRoomImg.id,
                                        qRoomImg.name,
                                        qRoomImg.ext))),
                        groupByReview
                ))
                .from(qDorm)
                .join(qDorm.dcategory, qDcategory)
                .join(qDorm.user, qUsers)
                .join(qRoom).on(qDorm.id.eq(qRoom.dorm.id))
                .leftJoin(qRoomImg).on(qRoom.id.eq(qRoomImg.room.id))
                .where(qRoom.price.eq(groupByDorm),
                        qRoomImg.id.in(groupImg))
                .fetch();

        for (DormDto dormDto : dormDtoList) {
            List<RoomDto> roomDtos = queryFactory
                    .select(Projections.constructor(RoomDto.class,
                            qRoom.id,
                            qRoom.person,
                            qRoom.price,
                            list(Projections.constructor(ImgDto.class,
                                    qRoomImg.id,
                                    qRoomImg.name,
                                    qRoomImg.ext))
                            // 이미지 리스트는 포함하지 않음
                    ))
                    .from(qRoom)
                    .leftJoin(qRoomImg).on(qRoom.id.eq(qRoomImg.room.id))
                    .where(qRoom.dorm.id.eq(dormDto.getId()))
                    .fetch();

            for (RoomDto roomDto : roomDtos) {
                List<ImgDto> images = queryFactory
                        .select(Projections.constructor(ImgDto.class,
                                qRoomImg.id,
                                qRoomImg.name,
                                qRoomImg.ext
                        ))
                        .from(qRoomImg)
                        .where(qRoomImg.room.id.eq(roomDto.getId()))
                        .fetch();

                roomDto.setImages(images);
                dormDto.setRoom(roomDto);
            }
        }
        /* List<DormDto> dormDtoList = queryFactory
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
                                        qRoom.price,
                                        list(Projections.constructor(ImgDto.class,
                                                qRoomImg.id,
                                                qRoomImg.name,
                                                qRoomImg.ext))),
                                groupByReview
                        ))
                        .from(qDorm)
                        .join(qDorm.dcategory, qDcategory)
                        .join(qDorm.user, qUsers)
                        .join(qRoom).on(qDorm.id.eq(qRoom.dorm.id))
                        .leftJoin(qRoomImg).on(qRoom.id.eq(qRoomImg.room.id))
                        .where(qRoom.price.eq(groupByDorm),
                                qRoomImg.id.in(groupImg))
                        .fetch();*/


        /*List<Room> roomDtos = queryFactory
                .selectFrom(qRoom)
                .join(qRoom.images, qRoomImg)
                .fetch();

        List<RoomDto> rooms = roomDtos.stream()
                .map(room -> new RoomDto(room.getId(), room.getPerson(), room.getPrice(), room.getImages()))
                .collect(Collectors.toList());
        System.out.println("rooms : "+rooms);*/

        // https://jojoldu.tistory.com/342
        // 위에서 roomDto 사용해서 id랑 ㅇ미ㅣㅈ 묶어버리면 좋을거같당,,
        // 아니면 dorm에다가 roomImages 직접적으로 넣어서 묶어버리면 위 블로그에서 한거 딱 될거같기도함
        /*return dormDtoList.stream()
                .map(dormDto -> new DormDto(dormDto.getId(), dormDto.getName(), dormDto.getDescription(), dormDto.getContactNum(),
                        dormDto.getCity(), dormDto.getTown(), dormDto.getAddress(), dormDto.getDcategory(), dormDto.getUser(), dormDto.getRoom(), dormDto.getRate()))
                .collect(Collectors.toList());*/
        return dormDtoList;
        /*queryFactory
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
                        Projections.fields(RoomDto.class,
                                qRoom.id,
                                qRoom.person,
                                qRoom.price,
                                list(Projections.fields(ImgDto.class,
                                        qRoomImg.id,
                                        qRoomImg.name,
                                        qRoomImg.ext))
                                list(Projections.fields(ImgDto.class,
                                        qRoomImg.id,
                                        qRoomImg.name,
                                        qRoomImg.ext))
                        ),
                        groupByReview
                ))
                .from(qDorm)
                .join(qDorm.dcategory, qDcategory)
                .join(qDorm.user, qUsers)
                .join(qRoom).on(qDorm.id.eq(qRoom.dorm.id))
                .leftJoin(qRoomImg).on(qRoom.id.eq(qRoomImg.room.id))
//                .groupBy(qRoom.id)
                .groupBy(qDorm.id, qDorm.name, qDorm.description, qDorm.contactNum, qDorm.city, qDorm.town, qDorm.address,
                        qDcategory.id, qDcategory.name, qUsers.id, qUsers.name, qUsers.role,
                        qRoom.id, qRoom.person, qRoom.price)
                .where(qRoom.price.eq(groupByDorm))
                .fetch();*/
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

        JPQLQuery<Long> groupByDate = JPAExpressions
                .select(subBooking.room.id)
                .from(subBooking)
                .where(subBooking.checkIn.between(checkIn, checkOut),
                        subBooking.checkOut.between(checkIn, checkOut));

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
                                qRoom.price
//                                ,list(groupImg)
                        ),
                        groupByReview
                ))
                .from(qDorm)
                .join(qDorm.dcategory, qDcategory)
                .join(qDorm.user, qUsers)
                .join(qRoom).on(qDorm.id.eq(qRoom.dorm.id))
                .join(qRoomImg).on(qRoom.id.eq(qRoomImg.room.id))
                .where(groupBySearch)
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

        // checkIn, checkOut 날짜에 예약 없는 방 보여주기
        JPQLQuery<Long> groupByDate = JPAExpressions
                .select(subBooking.room.id)
                .from(subBooking)
                .where(subBooking.checkIn.between(checkIn, checkOut),
                        subBooking.checkOut.between(checkIn, checkOut));

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
                                qRoom.price
//                                ,list(groupImg)
                        ),
                        groupByReview
                ))
                .from(qDorm)
                .join(qDorm.dcategory, qDcategory)
                .join(qDorm.user, qUsers)
                .join(qRoom).on(qDorm.id.eq(qRoom.dorm.id))
                .join(qRoomImg).on(qRoom.id.eq(qRoomImg.room.id))
                .where(
                        groupBySearch,
                        qDorm.id.in(groupByFilter)
                )
                .fetch();
    }
}
