package com.danjam.roomImg;

import com.danjam.room.Room;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@ToString
@Table(name = "room_img")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class RoomImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "name_original")
    private String nameOriginal;

    private String size;

    private String ext;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    public RoomImg(String name, String nameOriginal, String size, String ext, Room room) {
        this.name = name;
        this.nameOriginal = nameOriginal;
        this.size = size;
        this.ext = ext;
        this.room = room;
    }
}
