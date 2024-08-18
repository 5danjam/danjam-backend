package com.danjam.room;

import com.danjam.booking.Booking;
import com.danjam.dorm.Dorm;
import com.danjam.roomImg.RoomImg;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@ToString
@Table(name = "room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private int person;
    private int price;
    private String type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dorm_id")
    @JsonBackReference
    private Dorm dorm;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Booking> bookings;

    @OneToMany(mappedBy = "room")
    private List<RoomImg> images;

    @Builder
    public Room(String name, String description, int person, int price, String type, Dorm dorm) {
        this.name = name;
        this.description = description;
        this.person = person;
        this.price = price;
        this.type = type;
        this.dorm = dorm;
    }
}
