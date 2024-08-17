package com.danjam.amenity;

import jakarta.persistence.*;
import lombok.*;


@Data
@Entity
@ToString
@Table(name = "amenity")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글 고유

    private String name;

    @Builder
    public Amenity(Long id , String name){
        this.id = id;
        this.name = name;

    }
}
