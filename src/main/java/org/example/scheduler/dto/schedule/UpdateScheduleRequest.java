package org.example.scheduler.dto.schedule;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UpdateScheduleRequest {
    @NotBlank(message = "제목을 입력해주세요.")
    @Size(min = 2, max = 50, message = "제목은 2~30자 사이입니다.")
    private String title;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
