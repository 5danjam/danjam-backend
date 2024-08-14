package com.danjam.search.querydsl;

import com.danjam.users.Role;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String name;
    private Role role;

    @Builder
    public UserDto(Long id, String name, Role role) {
        this.id = id;
        this.name = name;
        this.role = role;
    }
}
