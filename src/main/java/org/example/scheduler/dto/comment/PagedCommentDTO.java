package org.example.scheduler.dto.comment;

import lombok.Getter;
import org.example.scheduler.entity.Comment;

import java.time.LocalDateTime;

@Getter
public class PagedCommentDTO {
    private final Long id;
    private final String content;
    private final String userName;
    private final Long scheduleId;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public PagedCommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.userName = comment.getUser().getName();
        this.scheduleId = comment.getSchedule().getId();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

}
