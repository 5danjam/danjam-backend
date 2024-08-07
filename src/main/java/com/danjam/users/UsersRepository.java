package com.danjam.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    Users save(Users users);

    Users findByEmailAndPassword(String email, String password);

    List<Users> findAll();

    Users findByEmail(String email);
}
