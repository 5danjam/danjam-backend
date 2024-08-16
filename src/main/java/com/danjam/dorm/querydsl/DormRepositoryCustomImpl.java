package com.danjam.dorm.querydsl;

import com.danjam.d_category.Dcategory;
import com.danjam.d_category.DcategoryListDTO;
import com.danjam.d_category.QDcategory;
import com.danjam.dorm.Dorm;
import com.danjam.dorm.QDorm;
import com.danjam.users.QUsers;
import com.danjam.users.Users;
import com.danjam.users.UsersDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class DormRepositoryCustomImpl implements DormRepositoryCustom {
    private final JPAQueryFactory queryFactory;

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
