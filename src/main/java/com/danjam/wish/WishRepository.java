package com.danjam.wish;

import com.danjam.dorm.Dorm;
import com.danjam.users.Users;
import com.danjam.wish.querydsl.WishQuerydslRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WishRepository extends JpaRepository<Wish, Long>, WishQuerydslRepository {

    @EntityGraph(attributePaths = {"users", "dorm"})
    @Query("select w from Wish w where w.users.id = :userId")
    Page<Wish> findWishesOrderByCreatedAtDesc(Long userId, Pageable pageable);

    Optional<Wish> findByUsersAndDorm(Users user, Dorm dorm);

    boolean existsByUsersAndDorm(Users user, Dorm dorm);
}
