package com.danjam.wish;

import com.danjam.dorm.Dorm;
import com.danjam.dorm.DormResponse;
import com.danjam.users.Users;
import com.danjam.users.UsersResponse;
import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishResponse {

    private Long id;
    private UsersResponse user;
    private DormResponse dorm;

    public static WishResponse fromEntity(final Wish wish) {
        UsersResponse usersResponse = getUsersResponse(wish);
        DormResponse dormResponse = getDormResponse(wish);

        return WishResponse.builder()
                .id(wish.getId())
                .user(usersResponse)
                .dorm(dormResponse)
                .build();
    }

    private static UsersResponse getUsersResponse(final Wish wish) {
        Users findUsers = wish.getUsers();
        return UsersResponse.builder()
                .id(findUsers.getId())
                .email(findUsers.getEmail())
                .name(findUsers.getName())
                .phoneNumber(findUsers.getPhoneNum())
                .build();
    }

    private static DormResponse getDormResponse(final Wish wish) {
        Dorm findDorm = wish.getDorm();
        return DormResponse.builder()
                .id(findDorm.getId())
                .name(findDorm.getName())
                .description(findDorm.getDescription())
                .build();
    }
}
