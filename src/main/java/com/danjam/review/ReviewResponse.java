package com.danjam.review;

import com.danjam.users.UsersResponse;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponse {

    private Long id;

    private UsersResponse users;


}
