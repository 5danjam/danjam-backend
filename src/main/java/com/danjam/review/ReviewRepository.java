package com.danjam.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {



        @Query(value = "SELECT v.* FROM review v " +
                "JOIN booking b ON v.booking_id = b.id " +
                "JOIN room r ON b.room_id = r.id " +
                "JOIN dorm d ON r.dorm_id = d.id " +
                "WHERE d.id = :dormId",
                countQuery = "SELECT count(*) FROM review v " +
                        "JOIN booking b ON v.booking_id = b.id " +
                        "JOIN room r ON b.room_id = r.id " +
                        "JOIN dorm d ON r.dorm_id = d.id " +
                        "WHERE d.id = :dormId",
                nativeQuery = true)
        Page<Review> findReviewsByDormId(@Param("dormId") Long dormId, Pageable pageable);


}
