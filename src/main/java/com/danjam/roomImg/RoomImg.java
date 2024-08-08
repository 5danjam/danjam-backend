package com.danjam.roomImg;

import com.danjam.room.Room;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Entity
@Table(name = "room_img")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class RoomImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String name_original;

    private String size;

    private String ext;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Builder
    public RoomImg(String name, String name_original, String size, String ext, Room room) {
        this.name = name;
        this.name_original = name_original;
        this.size = size;
        this.ext = ext;
        this.room = room;
    }
}
