package com.danjam.search.querydsl;

import com.danjam.room.Room;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ImgDto {
    private String name;
    private String nameOriginal;
    private String size;
    private String ext;

    @Builder
    public ImgDto(String name, String nameOriginal, String size, String ext) {
        this.name = name;
        this.nameOriginal = nameOriginal;
        this.size = size;
        this.ext = ext;
    }
}
