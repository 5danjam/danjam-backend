package com.danjam.category;

import com.danjam.dorm.DormDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class DCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<DormDTO> dormDTOS;
}

