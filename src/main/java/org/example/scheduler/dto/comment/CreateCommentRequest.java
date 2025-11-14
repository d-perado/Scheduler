package org.example.scheduler.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateCommentRequest {
    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
