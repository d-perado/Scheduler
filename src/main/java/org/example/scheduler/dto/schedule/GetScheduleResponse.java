package org.example.scheduler.dto.schedule;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetScheduleResponse {
    private final Long id;
    private final String title;
    private final String content;
    private final String writer;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public GetScheduleResponse(ScheduleDTO scheduleDTO){
        this.id = scheduleDTO.getId();
        this.title = scheduleDTO.getTitle();
        this.content = scheduleDTO.getContent();
        this.writer = scheduleDTO.getWriter();
        this.createdAt = scheduleDTO.getCreatedAt();
        this.updatedAt = scheduleDTO.getUpdatedAt();
    }
}
