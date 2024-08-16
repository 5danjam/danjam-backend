package com.danjam.users;

import com.danjam.users.querydsl.UsersListDTO;
import com.danjam.users.querydsl.UsersRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long>, UsersRepositoryCustom {
    Users save(Users users);

    Users findByEmailAndPassword(String email, String password);

    List<Users> findAll();

    Users findByEmail(String email);

}
