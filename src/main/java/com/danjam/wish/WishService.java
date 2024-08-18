package com.danjam.wish;

import com.danjam.dorm.Dorm;
import com.danjam.users.Users;
import com.danjam.wish.querydsl.WishDTO;
import com.danjam.wish.querydsl.WishWithSliceResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final WishRepository wishRepository;

    public Slice<WishWithSliceResponse> findAllByUsersById(Long id, Pageable pageable) {
        List<WishWithSliceResponse> wishes = wishRepository.findWishes(id, pageable);

        boolean hasNext = hasNextPage(wishes, pageable.getPageSize());

        return new SliceImpl<>(
                wishes,
                pageable,
                hasNext
        );
    }

    private boolean hasNextPage(List<WishWithSliceResponse> wishes, int pageSize) {
        if (wishes.size() > pageSize) {
            wishes.remove(pageSize);
            return true;
        }
        return false;
    }

    public List<WishDTO> findWishesById(Long id) {
        List<WishDTO> wishes = wishRepository.findWishesById(id);

        return wishes;
    }

    public boolean saveWish(Long userId, Long dormId) {
        Users user = Users.builder()
                .id(userId)
                .build();
        Dorm dorm = Dorm.builder()
                .id(dormId)
                .build();

        boolean existsWish = wishRepository.existsByUsersAndDorm(user, dorm);
        if (existsWish) {
            return false;
        }

        Wish wish = Wish.builder()
                .users(user)
                .dorm(dorm)
                .build();

        wishRepository.save(wish);

        return true;
    }

    public boolean deleteWish(Long userId, Long dormId) {
        Users user = Users.builder()
                .id(userId)
                .build();
        Dorm dorm = Dorm.builder()
                .id(dormId)
                .build();

        Wish wish = wishRepository.findByUsersAndDorm(user, dorm)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 Wish 입니다."));

        wishRepository.delete(wish);

        return !wishRepository.existsByUsersAndDorm(user, dorm);
    }
}
