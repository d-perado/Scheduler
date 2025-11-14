package org.example.scheduler.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {
    @NotNull
    private Long id;
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
