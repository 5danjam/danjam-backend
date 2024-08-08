package com.danjam.booking;

import com.danjam.payment.Payment;
import com.danjam.room.Room;
import com.danjam.users.Users;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.util.Date;

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
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    private int person;
    private Date check_in;
    private Date check_out;
    @ColumnDefault("N")
    private String status;


    @Builder
    public Booking(Long id, Users users, Room room, Payment payment, int person, Date check_in, Date check_out, String status) {
        this.id = id;
        this.users = users;
        this.room = room;
        this.payment = payment;
        this.person = person;
        this.check_in = check_in;
        this.check_out = check_out;
        this.status = status;
    }
}
