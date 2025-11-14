package org.example.scheduler.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateUserRequest {
    @NotNull(message = "이름을 입력해주세요.")
    private String name;
    @NotNull(message = "이메일을 입력해주세요.")
    private String email;
    @NotNull(message = "패스워드를 입력해주세요.")
    private String password;
}
