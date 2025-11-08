package org.example.scheduler.controller;

import org.example.scheduler.dto.CreateScheduleRequest;
import org.example.scheduler.dto.CreateScheduleResponse;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.dto.GetScheduleResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.scheduler.service.ScheduleService;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> handleCreateSchedule(
            @RequestBody CreateScheduleRequest request) {
        CreateScheduleResponse result = scheduleService.createSchedule(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> handleGetSchedule(
            @PathVariable Long scheduleId) {
        GetScheduleResponse result = scheduleService.getSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
