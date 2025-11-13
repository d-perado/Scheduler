package org.example.scheduler.dto.auth;

import lombok.Getter;
import org.example.scheduler.dto.user.SessionUserDTO;

@Getter
public class LoginResponse {
    private final Long id;
    private final String email;

    public LoginResponse(SessionUserDTO sessionUserDTO) {
        this.id = sessionUserDTO.getId();
        this.email = sessionUserDTO.getEmail();
    }
}
