package org.example.scheduler.dto.user;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetUserResponse {
    private final Long id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public GetUserResponse(UserDTO userDTO) {
        this.id = userDTO.getId();
        this.name = userDTO.getName();
        this.email = userDTO.getEmail();
        this.createdAt = userDTO.getCreatedAt();
        this.updatedAt = userDTO.getUpdatedAt();
    }
}
