package org.example.scheduler.auth.dto;

import lombok.Getter;
import org.example.scheduler.user.dto.SessionUserDTO;

@Getter
public class LoginResponse {
    private final Long id;
    private final String email;

    public LoginResponse(SessionUserDTO sessionUserDTO) {
        this.id = sessionUserDTO.getId();
        this.email = sessionUserDTO.getEmail();
    }
}
