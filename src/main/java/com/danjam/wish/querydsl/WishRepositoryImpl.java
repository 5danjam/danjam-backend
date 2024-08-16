package com.danjam.wish.querydsl;

import com.danjam.dorm.QDorm;
import com.danjam.users.QUsers;
import com.danjam.wish.QWish;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class WishRepositoryImpl implements WishQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<WishWithSliceResponse> findWishes(Long userId, Pageable pageable) {
        QWish qWish = QWish.wish;
        QUsers qUsers = QUsers.users;
        QDorm qDorm = QDorm.dorm;

        return queryFactory
                .select(Projections.constructor(WishWithSliceResponse.class,
                        qWish.id,
                        qWish.users.id.as("userId"),
                        qWish.dorm.id.as("dormId"),
                        qWish.dorm.name.as("dormName"),
                        qWish.dorm.description.as("dormDescription")))
                .from(qWish)
                .join(qWish.users, qUsers)
                .join(qWish.dorm, qDorm)
                .where(qUsers.id.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();
    }

    @Override
    public List<WishDTO> findWishesById(Long userId) {
        QWish qWish = QWish.wish;
        QUsers qUsers = QUsers.users;
        QDorm qDorm = QDorm.dorm;

        return queryFactory
                .select(Projections.constructor(WishDTO.class,
                        qWish.id,
                        qWish.users.id.as("userId"),
                        qWish.dorm.id.as("dormId")
                ))
                .from(qWish)
                .join(qWish.users, qUsers)
                .join(qWish.dorm, qDorm)
                .where(qUsers.id.eq(userId))
                .fetch();
    }
}
