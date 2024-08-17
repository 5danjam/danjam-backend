package com.danjam.search;

import com.danjam.search.querydsl.DormDto;
import com.danjam.search.querydsl.FilterDto;
import com.danjam.search.querydsl.SearchRepo;
import com.querydsl.core.Tuple;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SearchService {
    private final SearchRepo searchRepo;

    public List<DormDto> findList(SearchDto searchDto) {
        return searchRepo.findList(searchDto);
    }

    public List<DormDto> findAllList() {
        return searchRepo.findAllList();
    }

    public List<String> findByCity(String city) {
        return searchRepo.findByCity(city);
    }

    public List<DormDto> findByFilter(FilterDto filterDto) {
        return searchRepo.findByFilter(filterDto);
    }

}


/*
 public List<Long> findDormsWithCheapestAvailableRooms(LocalDate checkIn, LocalDate checkOut) {
        QRoom room = QRoom.room;
        QBooking booking = QBooking.booking;
        QDorm dorm = QDorm.dorm;

        // Subquery to find available rooms
        JPAQuery<Long> availableRoomsSubquery = new JPAQuery<>(queryFactory.getEntityManager());
        availableRoomsSubquery
            .select(room.id)
            .from(room)
            .leftJoin(booking).on(room.id.eq(booking.roomId))
            .where(
                booking.roomId.isNull()
                .or(
                    booking.checkIn.loe(checkOut)
                    .and(booking.checkOut.goe(checkIn))
                )
            );

        // Subquery to find cheapest rooms per dorm
        JPAQuery<Tuple> cheapestRoomsQuery = new JPAQuery<>(queryFactory.getEntityManager());
        cheapestRoomsQuery
            .select(room.dormId, room.price.min())
            .from(room)
            .where(room.id.in(availableRoomsSubquery))
            .groupBy(room.dormId);

        // Subquery to find the minimum price of the cheapest rooms
        JPAQuery<BigDecimal> minPriceQuery = new JPAQuery<>(queryFactory.getEntityManager());
        minPriceQuery
            .select(cheapestRoomsQuery.select(cheapestRoomsQuery.getMetadata().getExpressions().get(1)).min())
            .from(cheapestRoomsQuery);

        // Main query to find dorm IDs with the minimum price
        JPAQuery<Long> resultQuery = new JPAQuery<>(queryFactory.getEntityManager());
        resultQuery
            .select(dorm.id)
            .from(dorm)
            .innerJoin(
                JPAExpressions
                    .select(room.dormId, room.price.min())
                    .from(room)
                    .where(room.id.in(availableRoomsSubquery))
                    .groupBy(room.dormId),
                room.dormId.eq(dorm.id)
            )
            .where(
                JPAExpressions
                    .select(cheapestRoomsQuery.select(cheapestRoomsQuery.getMetadata().getExpressions().get(1)).min())
                    .from(cheapestRoomsQuery)
                    .eq(minPriceQuery)
            );

        return resultQuery.fetch();
*/