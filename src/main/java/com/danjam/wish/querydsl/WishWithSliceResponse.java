package com.danjam.wish.querydsl;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishWithSliceResponse {

    private Long id;
    private Long userId;
    private Long dormId;
    private String dormName;
    private String dormDescription;
}
