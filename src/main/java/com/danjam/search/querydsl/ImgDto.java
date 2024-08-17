package com.danjam.search.querydsl;

import com.danjam.room.Room;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImgDto {
    private Long id;
    private String name;
    private String ext;

    @Builder
    public ImgDto(Long id, String name, String ext) {
        this.id = id;
        this.name = name;
        this.ext = ext;
    }
}
