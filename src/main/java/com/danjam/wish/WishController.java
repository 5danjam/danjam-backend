package com.danjam.wish;

import com.danjam.wish.querydsl.WishDTO;
import com.danjam.wish.querydsl.WishWithSliceResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wishes")
@RequiredArgsConstructor
public class WishController {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final WishService wishService;

    @GetMapping("/{id}")
    public ResponseEntity<Slice<WishWithSliceResponse>> getAllWishes(
            @PathVariable Long id,
            @PageableDefault(size = 25)
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Slice<WishWithSliceResponse> wishResponsePage = wishService.findAllByUsersById(id, pageable);

        return new ResponseEntity<>(wishResponsePage, HttpStatus.OK);
    }


    @GetMapping("/wish/{id}")
    public ResponseEntity<List<WishDTO>> findAllByUsersById(@PathVariable Long id) {
        List<WishDTO> wishDTOList = wishService.findWishesById(id);

        return new ResponseEntity<>(wishDTOList, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/{userId}/{dormId}")
    public ResponseEntity<Map<String, String>> saveWish(@PathVariable Long userId, @PathVariable Long dormId) {
        Map<String, String> result = new HashMap<>();

        boolean isSaved = wishService.saveWish(userId, dormId);
        if (!isSaved) {
            result.put("result", "fail");
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }

        result.put("result", "success");
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/{userId}/{dormId}")
    public ResponseEntity<Map<String, String>> deleteWish(@PathVariable Long userId, @PathVariable Long dormId) {
        Map<String, String> result = new HashMap<>();

        boolean isDeleted = wishService.deleteWish(userId, dormId);
        if (!isDeleted) {
            result.put("result", "fail");
            return new ResponseEntity<>(result, HttpStatus.NO_CONTENT);
        }

        result.put("result", "success");
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
