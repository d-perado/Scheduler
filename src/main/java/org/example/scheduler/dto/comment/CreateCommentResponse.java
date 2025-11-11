package org.example.scheduler.dto.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateCommentResponse {
    private final Long id;
    private final String content;
    private final Long userId;
    private final Long scheduleId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CreateCommentResponse(CommentDTO commentDTO) {
        this.id = commentDTO.getId();
        this.content = commentDTO.getContent();
        this.userId = commentDTO.getUser().getId();
        this.scheduleId = commentDTO.getSchedule().getId();
        this.createdAt = commentDTO.getCreatedAt();
        this.updatedAt = commentDTO.getUpdatedAt();
    }
}
