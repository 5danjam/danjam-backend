package com.danjam.users.querydsl;


import com.danjam.users.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class UsersListDTO {
    private Long id;
    private String name;
    private String email;
    private int phoneNum;
    private Role role;
    private String status;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;


    public UsersListDTO(Long id, String name, String email, int phoneNum,
                        Role role, LocalDateTime createAt, LocalDateTime updateAt,
                        String status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNum = phoneNum;
        this.role = role;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.status = status;
    }

}
