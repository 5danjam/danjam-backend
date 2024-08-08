package com.danjam.amenity;

import com.danjam.dorm.DormDTO;
import jakarta.persistence.*;

@Entity
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;


    @ManyToOne
    @JoinColumn(name = "dorm_Id")
    private DormDTO dormDTO;
}
