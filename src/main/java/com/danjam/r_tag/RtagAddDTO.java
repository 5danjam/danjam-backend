package com.danjam.r_tag;

import com.danjam.amenity.Amenity;
import com.danjam.d_amenity.Damenity;
import com.danjam.dorm.Dorm;
import com.danjam.review.Review;
import com.danjam.tag.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RtagAddDTO {

    private Tag tag;
    private List<Long> tagId;

    private Review review;
    private Long reviewId;

    public Rtag toEntity(){
        return Rtag.builder()
                .tag(tag)
                .review(review)
                .build();
    }



}

