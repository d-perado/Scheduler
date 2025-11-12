package org.example.scheduler.dto.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UpdateCommentResponse {
    private final Long id;
    private final String content;
    private final String userName;
    private final Long scheduleId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UpdateCommentResponse(CommentDTO commentDTO) {
        this.id = commentDTO.getId();
        this.content = commentDTO.getContent();
        this.userName = commentDTO.getUserName();
        this.scheduleId = commentDTO.getScheduleId();
        this.createdAt = commentDTO.getCreatedAt();
        this.updatedAt = commentDTO.getUpdatedAt();
    }
}