package com.danjam.users;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
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
    private Date createDate;
    private Date updateDate;

    @Builder
    public UsersDto(Long id, String email, String name, int phoneNum) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneNum = phoneNum;
    }

    public Users toEntity() {
        return Users.builder()
                .id(id)
                .email(email)
                .password(password)
                .name(name)
                .phoneNum(phoneNum)
                .role(role)
                .status(status)
                .createDate(createDate)
                .updateDate(updateDate)
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
