package com.danjam.review;

import com.danjam.r_tag.RtagAddDTO;
import com.danjam.r_tag.RtagServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/review")
public class ReviewController {

    private final ReviewServiceImpl REVIEWSERVICE;
    private final RtagServiceImpl RTAGSERVICE;
    private final ReviewRepository REVIEWREPOSITORY;


    @PostMapping("/write")
    public HashMap<String, Object> write(@RequestBody ReviewWithTagsDTO reviewWithTagsDTO) {

        HashMap<String, Object> resultMap = new HashMap();

        System.out.println("reviewWithTagsDTO = " + reviewWithTagsDTO);
        System.out.println("reviewAddDTO: " + reviewWithTagsDTO.getReview());
        System.out.println("tags: " + reviewWithTagsDTO.getTags());

        try {
            Long savedReview = REVIEWSERVICE.write(reviewWithTagsDTO.getReview());

            if (reviewWithTagsDTO.getTags() != null && !reviewWithTagsDTO.getTags().isEmpty()) {

                for (Long tagId : reviewWithTagsDTO.getTags()) {
                    RtagAddDTO rtagAddDTO = RtagAddDTO.builder()
                            .reviewId(savedReview)
                            .tagId(List.of(tagId))
                            .build();

                    RTAGSERVICE.insert(rtagAddDTO);
                }
            }
            resultMap.put("result", "success");

        } catch (Exception e) {
            e.printStackTrace();
            resultMap.put("result", "fail");
        }

        return resultMap;
    }


    @GetMapping("/{dormId}")
    public ResponseEntity<StatsAndReviewDTO> getReviewsByDormId(@PathVariable Long dormId) {
        StatsAndReviewDTO result = REVIEWSERVICE.getStatsAndReviews(dormId);
        System.out.println("result = " + result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    /*@GetMapping("/{dormId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByDormId(@PathVariable Long dormId) {
        List<ReviewDto> reviewDtos = REVIEWSERVICE.getReviewsByDormId(dormId);
        System.out.println("reviewDtos = " + reviewDtos);

        return new ResponseEntity<>(reviewDtos, HttpStatus.OK);
    }

    @GetMapping("/stats/{dormId}")
    public ResponseEntity<DormStatsDTO> getDormStatsByDormId(@PathVariable Long dormId) {
        DormStatsDTO dormStatsDTO = REVIEWSERVICE.getDormStatsByDormId(dormId);
        System.out.println("dormStatsDTO = " + dormStatsDTO);

        return new ResponseEntity<>(dormStatsDTO, HttpStatus.OK);
    }*/
}
