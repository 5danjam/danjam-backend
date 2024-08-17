package com.danjam.wish.querydsl;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishDTO {
    private Long id;
    private Long userId;
    private Long dormId;
}
