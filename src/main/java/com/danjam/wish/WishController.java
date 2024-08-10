package com.danjam.wish;

import com.danjam.wish.querydsl.WishWithSliceResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wishes")
@RequiredArgsConstructor
public class WishController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final WishService wishService;

    @GetMapping("/{id}")
    public ResponseEntity<Slice<WishWithSliceResponse>> getAllWishes(
            @PathVariable Long id,
            @PageableDefault(size = 2)
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Slice<WishWithSliceResponse> wishResponsePage = wishService.findAllByUsersById(id, pageable);

        return new ResponseEntity<>(wishResponsePage, HttpStatus.OK);
    }
}
