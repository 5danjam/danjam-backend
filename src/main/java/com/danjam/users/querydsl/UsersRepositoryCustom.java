package com.danjam.users.querydsl;

import java.util.List;

public interface UsersRepositoryCustom {

    List<UsersListDTO> findUsersList();

}
