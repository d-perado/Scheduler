package org.example.scheduler.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateUserRequest {
    @NotBlank
    private Long id;

    @NotBlank(message = "이름을 입력해주세요.")
    @Size(min = 2, max = 30, message = "이름은 2~30자까지만 가능합니다.")
    private String name;

    @NotBlank(message = "패스워드를 입력해주세요.")
    private String password;
}
