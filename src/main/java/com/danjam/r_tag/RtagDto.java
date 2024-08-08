package com.danjam.r_tag;

import com.danjam.review.Review;
import com.danjam.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RtagDto {
    private Long id;
    private Review review;
    private Tag tag;

    public Rtag toEntity() {
        return Rtag.builder()
                .review(review)
                .tag(tag)
                .build();
    }
}
