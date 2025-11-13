package org.example.scheduler.controller;

import jakarta.validation.Valid;
import org.example.scheduler.dto.schedule.*;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.dto.user.SessionUserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.scheduler.service.ScheduleService;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/api/private/schedules")
    public ResponseEntity<CreateScheduleResponse> handlerCreateSchedule(
            @Valid @RequestBody CreateScheduleRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUserDTO sessionUser
    ) {
        CreateScheduleResponse result = scheduleService.createSchedule(request, sessionUser.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> handlerGetSchedule(
            @PathVariable Long scheduleId
    ) {
        GetScheduleResponse result = scheduleService.getSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/api/private/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> handlerUpdateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUserDTO sessionUser
    ) {
        UpdateScheduleResponse result = scheduleService.updateSchedule(sessionUser.getId(), scheduleId, request);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/api/private/schedules/{scheduleId}")
    public ResponseEntity<Void> handlerDeleteSchedule(
            @PathVariable Long scheduleId,
            @SessionAttribute(name = "loginUser", required = false) SessionUserDTO sessionUser
    ) {
        scheduleService.deleteSchedule(sessionUser.getId(), scheduleId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/schedules/all")
    public ResponseEntity<Page<GetPagedScheduleResponse>> handlerGetPagedSchedule(
            @RequestParam int pageNo
    ) {
        Page<GetPagedScheduleResponse> result = scheduleService.getPagedSchedule(pageNo);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
