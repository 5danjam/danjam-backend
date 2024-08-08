package com.danjam.wish;

import com.danjam.dorm.Dorm;
import com.danjam.users.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishDto {
    private Long id;
    private Users users;
    private Dorm dorm;
    private LocalDateTime createdAt;

    public Wish toEntity() {
        return Wish.builder()
                .users(users)
                .dorm(dorm)
                .createdAt(createdAt)
                .build();
    }
}
