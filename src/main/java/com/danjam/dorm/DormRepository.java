package com.danjam.dorm;


import com.danjam.dorm.querydsl.DormRepositoryCustom;
import com.danjam.users.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DormRepository extends JpaRepository<Dorm, Long>, DormRepositoryCustom {

    @Override
    Optional<Dorm> findById(Long id);

    List<Dorm> findByUser(Users user);

    List<Dorm> findByStatus(String status);

    boolean existsByAddress(String address);
}
