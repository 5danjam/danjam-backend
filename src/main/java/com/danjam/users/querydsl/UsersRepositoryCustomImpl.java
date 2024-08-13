package com.danjam.users.querydsl;

import com.danjam.users.QUsers;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UsersRepositoryCustomImpl implements  UsersRepositoryCustom{
    @Autowired
    private JPAQueryFactory queryFactory;

    @Override
    public List<UsersListDTO> findUsersList() {
        QUsers users = QUsers.users;

        return queryFactory
                .select(Projections.constructor(
                        UsersListDTO.class,
                        users.id,
                        users.email,
                        users.name,
                        Expressions.stringTemplate("CONCAT('0', CAST({0} AS CHAR))", users.phoneNum).as("phoneNum"),
                        Expressions.stringTemplate(
                                "CASE WHEN {0} = 'ROLE_ADMIN' THEN '관리자' " +
                                        "WHEN {0} = 'ROLE_SELLER' THEN '판매자' " +
                                        "ELSE '일반고객' END",
                                users.role).as("role"),
                        Expressions.stringTemplate(
                                "CASE WHEN {0} = 'Y' THEN '활동중' " +
                                        "ELSE '휴면중' END",
                                users.status).as("status"),
                        users.createAt,
                        users.updateAt
                ))
                .from(users)
                .fetch();
    }

}
