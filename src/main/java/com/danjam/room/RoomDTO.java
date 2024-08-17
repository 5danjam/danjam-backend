package com.danjam.room;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class RoomDTO {
    private Long id;
    private String name;
    private String type;

    @Builder
    public RoomDTO(Long id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }
}
