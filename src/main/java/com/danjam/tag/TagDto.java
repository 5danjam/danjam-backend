package com.danjam.tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private Long id;
    private String name;
    private String PN;

    public Tag toEntity() {
        return Tag.builder()
                .name(name)
                .PN(PN)
                .build();
    }
}
