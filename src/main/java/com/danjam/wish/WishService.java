package com.danjam.wish;

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
}
