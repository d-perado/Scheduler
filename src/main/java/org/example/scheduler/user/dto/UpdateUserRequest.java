package org.example.scheduler.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @NotNull
    private Long id;

    @NotNull(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 30, message = "이름은 2~30자까지만 가능합니다.")
    private String name;

    @NotNull(message = "패스워드를 입력해주세요.")
    private String password;
}
