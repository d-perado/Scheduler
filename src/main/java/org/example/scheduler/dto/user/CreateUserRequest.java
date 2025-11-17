package org.example.scheduler.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CreateUserRequest {
    @NotNull(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 30)
    private String name;
    @NotNull(message = "이메일을 입력해주세요.")
    @Email
    private String email;
    @NotNull(message = "패스워드를 입력해주세요.")
    private String password;
}
