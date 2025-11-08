package org.example.scheduler.dto.user;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateUserRequest {
    private String name;
    private String email;
    private String password;
}
