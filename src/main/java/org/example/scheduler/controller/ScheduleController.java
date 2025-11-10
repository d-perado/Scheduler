package org.example.scheduler.controller;

import org.example.scheduler.dto.schedule.*;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.dto.user.SessionUserDTO;
import org.example.scheduler.repository.UserRepository;
import org.example.scheduler.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.scheduler.service.ScheduleService;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final UserService userService;

    @PostMapping("/schedules")
    public ResponseEntity<CreateScheduleResponse> handleCreateSchedule(
            @RequestBody CreateScheduleRequest request,
            @SessionAttribute(name = "loginUser",required = false) SessionUserDTO sessionUser
    ) {
        CreateScheduleResponse result = scheduleService.createSchedule(request, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> handleGetSchedule(
            @PathVariable Long scheduleId
    ) {
        GetScheduleResponse result = scheduleService.getSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> handleUpdateSchedule(
            @PathVariable Long scheduleId,
            @RequestBody UpdateScheduleRequest request,
            @SessionAttribute(name = "loginUser",required = false) SessionUserDTO sessionUser
    ) {
        UpdateScheduleResponse result = scheduleService.updateSchedule(sessionUser.getId(), scheduleId, request);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/schedules/{scheduleId}")
    public ResponseEntity<Void> handleDeleteSchedule(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = "loginUser",required = false) SessionUserDTO sessionUser
    ) {
        scheduleService.deleteSchedule(sessionUser.getId(), scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
