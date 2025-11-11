package org.example.scheduler.dto.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetCommentResponse {
    private final Long id;
    private final String content;
    private final Long userId;
    private final Long scheduleId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public GetCommentResponse(CommentDTO commentDTO) {
        this.id = commentDTO.getId();
        this.content = commentDTO.getContent();
        this.userId = commentDTO.getUser().getId();
        this.scheduleId = commentDTO.getSchedule().getId();
        this.createdAt = commentDTO.getCreatedAt();
        this.updatedAt = commentDTO.getUpdatedAt();
    }
}