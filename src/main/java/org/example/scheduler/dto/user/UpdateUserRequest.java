package org.example.scheduler.dto.user;

import lombok.Getter;

@Getter
public class UpdateUserRequest {
    private Long id;
    private String name;
    private String email;
}
