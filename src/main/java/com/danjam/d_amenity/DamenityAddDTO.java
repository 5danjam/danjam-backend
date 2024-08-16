package com.danjam.d_amenity;

import com.danjam.amenity.Amenity;
import com.danjam.dorm.Dorm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DamenityAddDTO {

    private Amenity amenity; // 카테고리
    private List<Long> amenityId;

    private Dorm dorm; // 판매자
    private Long dormId;

    public Damenity toEntity(){
        return Damenity.builder()
                .amenity(amenity)
                .dorm(dorm)
                .build();
    }



}

