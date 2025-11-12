package org.example.scheduler.dto.comment;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class CreateCommentResponse {
    private final Long id;
    private final String content;
    private final String userName;
    private final Long scheduleId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CreateCommentResponse(CommentDTO commentDTO) {
        this.id = commentDTO.getId();
        this.content = commentDTO.getContent();
        this.userName = commentDTO.getUserName();
        this.scheduleId = commentDTO.getScheduleId();
        this.createdAt = commentDTO.getCreatedAt();
        this.updatedAt = commentDTO.getUpdatedAt();
    }
}
