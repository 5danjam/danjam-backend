package com.danjam.review;

import com.danjam.dorm.DormDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByDormOrderByRatingDesc(DormDTO dormDTO);
}
