package org.example.scheduler.dto.schedule;

import lombok.Getter;
import org.example.scheduler.entity.Schedule;

import java.time.LocalDateTime;

@Getter
public class GetPagedScheduleResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final int commentCount;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;


    public GetPagedScheduleResponse(Schedule schedule, int commentCount) {
        this.id = schedule.getId();
        this.title = schedule.getTitle();
        this.content = schedule.getContent();
        this.writer = schedule.getUser().getName();
        this.commentCount = commentCount;
        this.createdAt = schedule.getCreatedAt();
        this.updatedAt = schedule.getUpdatedAt();
    }
}
