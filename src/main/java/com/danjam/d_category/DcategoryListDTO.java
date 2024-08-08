package com.danjam.d_category;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DcategoryListDTO {
    private Long id; // 댓글 고유

    private String name;
}
