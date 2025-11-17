package org.example.scheduler.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UpdateCommentRequest {
    @NotBlank
    private Long id;

    @NotBlank(message = "내용을 입력해주세요.")
    private String content;
}
