package com.danjam.users;

import lombok.*;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersResponse {

    private Long id;
    private String email;
    private String name;
    private int phoneNumber;
}
