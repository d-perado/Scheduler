package org.example.scheduler.dto.user;

import lombok.Getter;

@Getter
public class SessionUserDTO {
    private final Long id;
    private final String email;

    public SessionUserDTO(Long userId, String userEmail) {
        this.id = userId;
        this.email = userEmail;
    }
}
