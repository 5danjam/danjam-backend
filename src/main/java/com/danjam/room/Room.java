package com.danjam.room;

import com.danjam.dorm.DormDTO;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;
    private String size;

    private int capacity; //수용인원
    private int bedCount;

    private double price;

    @ManyToOne
    @JoinColumn(name = "dorm_id")
    private DormDTO dormDTO;

    @OneToMany(mappedBy = "room")
    private List<RoomImg> images;
}
