package org.example.scheduler.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @NotNull
    private Long id;
    @NotNull(message = "이름을 입력해주세요.")
    private String name;
    @NotNull(message = "패스워드를 입력해주세요.")
    private String password;
}
