package com.danjam.dorm;

import lombok.Data;

import java.util.List;

@Data
public class DormDTO {
    private Long id;
    private String name;
    private String description;
    private String contactNum;
    private String city;
    private String town;
    private String address;
    private String status;
     private List<String> dormImages;
    // List<String> dormImages 생성자 안에 있어야 함.
    // 생성자, getter, setter
    public DormDTO(Long id, String name, String description, String contactNum,
                   String city, String town, String address, String status, List<String> dormImages ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.contactNum = contactNum;
        this.city = city;
        this.town = town;
        this.address = address;
        this.status = status;
        this.dormImages = dormImages;
    }

}
