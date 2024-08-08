package com.danjam.room;

import jakarta.persistence.*;

@Entity
public class RoomImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;
}
