package com.danjam.Dcategory;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "d_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dcategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 댓글 고유

    private String name;

    @Builder
    public Dcategory(Long id , String name){
        this.id = id;
        this.name = name;

    }
}
