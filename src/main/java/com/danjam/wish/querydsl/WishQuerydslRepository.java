package com.danjam.wish.querydsl;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WishQuerydslRepository {

    List<WishWithSliceResponse> findWishes(Long userId, Pageable pageable);

    List<WishDTO> findWishesById(Long userId);
}
