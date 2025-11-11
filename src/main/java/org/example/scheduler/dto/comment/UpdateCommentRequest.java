package org.example.scheduler.dto.comment;

import lombok.Getter;

@Getter
public class UpdateCommentRequest {
    private Long id;
    private String content;
}
