package com.danjam.dorm;

import com.danjam.category.DCategory;
import com.danjam.review.Review;
import com.danjam.room.Room;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
public class DormDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double price;

    private String name;
    private String description;
    private String address;
    private String amenities;
    private Timestamp createAt;
    private Timestamp updateAt;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private DCategory category;

    @OneToMany(mappedBy = "dorm")
    private List<Room> rooms;

    @OneToMany(mappedBy = "dorm")
    private List<Review> reviews;


}
