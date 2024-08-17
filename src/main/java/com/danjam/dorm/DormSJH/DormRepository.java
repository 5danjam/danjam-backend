package com.danjam.dorm;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DormRepository extends JpaRepository<Dorm,Long> {

    @Override
    Optional<Dorm> findById(Long id);


}
