package org.example.scheduler.dto.user;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateUserRequest {
    private Long id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
