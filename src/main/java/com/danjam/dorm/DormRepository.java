package com.danjam.dorm;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface DormRepository extends JpaRepository<Dorm,Long> {

    @Override
    Optional<Dorm> findById(Long id);

}
