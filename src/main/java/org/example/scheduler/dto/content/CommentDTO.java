package org.example.scheduler.dto.content;

import lombok.Getter;
import org.example.scheduler.entity.Comment;
import org.example.scheduler.entity.Schedule;
import org.example.scheduler.entity.User;

import java.time.LocalDateTime;

@Getter
public class CommentDTO {
    private final Long id;
    private final String content;
    private final User user;
    private final Schedule schedule;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.user = comment.getUser();
        this.schedule = comment.getSchedule();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
