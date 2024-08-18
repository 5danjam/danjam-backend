package com.danjam.dorm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DormListDTO {
    private Long id;

    private String name;

    private String description;

    private String contactNum;

    private String city;

    private String town;

    private String address;

    private String status;

    private List<String> roomImgNames;


    public Dorm toEntity() {
        return Dorm.builder()
                .id(id)
                .name(name)
                .description(description)
                .contactNum(contactNum)
                .city(city)
                .town(town)
                .address(address)
                .status(status)
                .build();
    }

}
