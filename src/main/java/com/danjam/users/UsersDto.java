package com.danjam.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDto {
    private Long id;
    private String email;
    private String password;
    private String name;
    private int phoneNum;
    private Role role;
    private String status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Users toEntity() {
        return Users.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .phoneNum(phoneNum)
                .role(role)
                .status(status)
                .createAt(createAt)
                .updateAt(updateAt)
                .build();
    }

    public static UsersDto fromEntity(final Users user) {
        return UsersDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .phoneNum(user.getPhoneNum())
                .build();
    }
}
