package com.danjam.review;

import com.danjam.d_amenity.DamenityAddDTO;
import com.danjam.wish.querydsl.WishWithSliceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewServiceImpl REVIEWSERVICE;


    @PostMapping("/review/write")
    public HashMap<String, Object> write(@RequestBody ReviewAddDTO reviewAddDTO) {

        HashMap<String, Object> resultMap = new HashMap();

        System.out.println("reviewAddDTO: "+ reviewAddDTO);

        try {
            REVIEWSERVICE.write(reviewAddDTO);
            resultMap.put("result", "success");

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }

        return resultMap;
    }

    @GetMapping("/review/{id}")
    public ResponseEntity<Slice<ReviewDto>> findReviewsByDormId(
            @PathVariable Long id,
            @PageableDefault(size = 25)
            @SortDefault(sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {
        Slice<ReviewDto> reviewDtoSlice = REVIEWSERVICE.findReviewsByDormId(id, pageable);

        return new ResponseEntity<>(reviewDtoSlice, HttpStatus.OK);
    }
}
