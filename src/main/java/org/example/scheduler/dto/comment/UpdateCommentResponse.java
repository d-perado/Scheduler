package org.example.scheduler.dto.comment;

import lombok.Getter;
import org.example.scheduler.entity.Comment;
import org.example.scheduler.entity.Schedule;
import org.example.scheduler.entity.User;

import java.time.LocalDateTime;

@Getter
public class UpdateCommentResponse {
    private final Long id;
    private final String content;
    private final User user;
    private final Schedule schedule;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public UpdateCommentResponse(CommentDTO commentDTO) {
        this.id = commentDTO.getId();
        this.content = commentDTO.getContent();
        this.user = commentDTO.getUser();
        this.schedule = commentDTO.getSchedule();
        this.createdAt = commentDTO.getCreatedAt();
        this.updatedAt = commentDTO.getUpdatedAt();
    }
}