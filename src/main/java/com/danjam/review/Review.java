package com.danjam.review;

import com.danjam.dorm.DormDTO;
import jakarta.persistence.*;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private double rating; // 디비에는 rate;

    @ManyToOne
    @JoinColumn(name = "dorm_id")
    private DormDTO dormDTO;

}

