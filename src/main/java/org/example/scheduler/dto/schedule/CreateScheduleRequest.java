package org.example.scheduler.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CreateScheduleRequest {
    private String title;
    private String content;
    private String writer;
}
