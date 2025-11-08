package org.example.scheduler.dto.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.scheduler.entity.User;

@Getter
@NoArgsConstructor
public class CreateScheduleRequest {
    private String title;
    private String content;
    private Long userId;
}
