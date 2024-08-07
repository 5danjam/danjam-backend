package com.danjam.Users;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {

    @Override
    Optional<Users> findById(Long userId);

}
