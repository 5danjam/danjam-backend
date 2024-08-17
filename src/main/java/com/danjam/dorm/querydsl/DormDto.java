package com.danjam.dorm.querydsl;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DormDto {
    private Long id;
    private String name;
    //    private String contactNum;
//    private String city;
//    private String town;
    private String address;
    private CategoryDto dcategory; //카테고리
    private UserDto user;

    @Builder
    public DormDto(Long id, String name, String address, CategoryDto dcategory, UserDto user) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.dcategory = dcategory;
        this.user = user;
    }
}
