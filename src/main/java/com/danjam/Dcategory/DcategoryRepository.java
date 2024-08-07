package com.danjam.Dcategory;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DcategoryRepository extends JpaRepository<Dcategory, Long> {
    @Override
    Optional<Dcategory> findById(Long id);

    @Override
    List<Dcategory> findAll();
}
