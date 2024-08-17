package com.danjam.r_tag;

import com.danjam.review.Review;
import com.danjam.review.ReviewRepository;
import com.danjam.tag.Tag;
import com.danjam.tag.TagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
public class RtagServiceImpl  {

    private final ReviewRepository REVIEWREPOSITORY;
    private final RtagRepository RTAGREPOSITORY;
    private final TagRepository TAGREPOSITORY;

    @Transactional
    public void insert(RtagAddDTO rtagAddDTO) {
        Review review = REVIEWREPOSITORY.findById(rtagAddDTO.getReviewId())
                .orElseThrow(() -> new RuntimeException("올바르지 않은 리뷰 Id"));

        // 여기 있던 rtag 아이디로 태그 찾는거 RtagAddDTO로 옮겼습니다

       /* for (Long tagId : rtagAddDTO.getTagId()) {
            Tag tag = TAGREPOSITORY.findById(tagId)
                    .orElseThrow(() -> new RuntimeException("tag not found"));

            Rtag rtag = Rtag.builder()
                    .tag(tag)
                    .review(review)
                    .build();

            RTAGREPOSITORY.save(rtag);
        }*/

        Rtag rtag = rtagAddDTO.toEntity(TAGREPOSITORY, review);

        RTAGREPOSITORY.save(rtag);
    }
}
