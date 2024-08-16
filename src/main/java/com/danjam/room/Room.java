package com.danjam.room;

import com.danjam.dorm.Dorm;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
    private Dorm dorm;

    @Builder
    public Room(String name, String description,  int person, int price,String type, Dorm dorm) {
        this.name = name;
        this.description = description;
        this.person = person;
        this.price = price;
        this.type = type;
        this.dorm = dorm;
    }
}
