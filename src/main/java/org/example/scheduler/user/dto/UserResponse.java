package org.example.scheduler.user.dto;

import lombok.Getter;
import org.example.scheduler.user.entity.User;

import java.time.LocalDateTime;

@Getter
public class UserResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.updatedAt = user.getUpdatedAt();
    }
}
