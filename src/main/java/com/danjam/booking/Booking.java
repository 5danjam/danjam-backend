package com.danjam.booking;

import com.danjam.payment.Payment;
import com.danjam.room.Room;
import com.danjam.users.Users;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@ToString
@Table(name = "booking")
@DynamicInsert // default
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonBackReference
    private Room room;



    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", unique = true)
    private Payment payment;

    private int person;

    @Column(name = "check_in")
    private LocalDateTime checkIn;

    @Column(name = "check_out")
    private LocalDateTime checkOut;

    @ColumnDefault("N")
    private String status;
  
    @Builder
    public Booking(Long id, Users users, Room room, Payment payment, int person, LocalDateTime checkIn, LocalDateTime checkOut, String status) {
        this.id = id;
        this.users = users;
        this.room = room;
        this.payment = payment;
        this.person = person;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.status = status;
    }
}
