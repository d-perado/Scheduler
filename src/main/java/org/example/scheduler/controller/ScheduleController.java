package org.example.scheduler.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.scheduler.dto.schedule.*;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.dto.user.SessionUserDTO;
import org.example.scheduler.util.exception.CustomException;
import org.example.scheduler.util.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.scheduler.service.ScheduleService;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    @PostMapping("/api/schedules")
    public ResponseEntity<CreateScheduleResponse> handlerCreateSchedule(
            @Valid @RequestBody CreateScheduleRequest request,
            HttpSession session
    ) {
        SessionUserDTO sessionUserDTO = (SessionUserDTO) session.getAttribute("loginUser");

        if (sessionUserDTO == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        CreateScheduleResponse result = scheduleService.createSchedule(request, sessionUserDTO.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<GetScheduleResponse> handlerGetSchedule(
            @PathVariable Long scheduleId
    ) {
        GetScheduleResponse result = scheduleService.getSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/api/schedules/{scheduleId}")
    public ResponseEntity<UpdateScheduleResponse> handlerUpdateSchedule(
            @PathVariable Long scheduleId,
            @Valid @RequestBody UpdateScheduleRequest request,
            HttpSession session
    ) {
        SessionUserDTO sessionUserDTO = (SessionUserDTO) session.getAttribute("loginUser");

        if (sessionUserDTO == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        UpdateScheduleResponse result = scheduleService.updateSchedule(sessionUserDTO.getId(), scheduleId, request);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/api/schedules/{scheduleId}")
    public ResponseEntity<Void> handlerDeleteSchedule(
            @PathVariable Long scheduleId,
            HttpSession session
    ) {
        SessionUserDTO sessionUserDTO = (SessionUserDTO) session.getAttribute("loginUser");

        scheduleService.deleteSchedule(sessionUserDTO.getId(), scheduleId);

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
