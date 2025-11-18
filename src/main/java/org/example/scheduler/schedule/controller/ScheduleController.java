package org.example.scheduler.schedule.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.user.dto.SessionUserDTO;
import org.example.scheduler.schedule.dto.CreateScheduleRequest;
import org.example.scheduler.schedule.dto.PagedScheduleResponse;
import org.example.scheduler.schedule.dto.ScheduleResponse;
import org.example.scheduler.schedule.dto.UpdateScheduleRequest;
import org.example.scheduler.util.exception.CustomException;
import org.example.scheduler.util.exception.ErrorCode;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.example.scheduler.schedule.service.ScheduleService;

@RestController
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

    //일정 생성
    @PostMapping("/api/schedules")
    public ResponseEntity<ScheduleResponse> handlerCreateSchedule(
            @Valid @RequestBody CreateScheduleRequest request, HttpSession session
    ) {
        SessionUserDTO sessionUserDTO = (SessionUserDTO) session.getAttribute("loginUser");

        if (sessionUserDTO == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        ScheduleResponse result = scheduleService.createSchedule(request, sessionUserDTO.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //일정 단건 조회
    @GetMapping("/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponse> handlerGetSchedule(
            @PathVariable Long scheduleId
    ) {
        ScheduleResponse result = scheduleService.getSchedule(scheduleId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //일정 수정
    @PatchMapping("/api/schedules/{scheduleId}")
    public ResponseEntity<ScheduleResponse> handlerUpdateSchedule(
            @PathVariable Long scheduleId, @Valid @RequestBody UpdateScheduleRequest request, HttpSession session
    ) {
        SessionUserDTO sessionUserDTO = (SessionUserDTO) session.getAttribute("loginUser");

        if (sessionUserDTO == null) {
            throw new CustomException(ErrorCode.UNAUTHORIZED);
        }

        ScheduleResponse result = scheduleService.updateSchedule(sessionUserDTO.getId(), scheduleId, request);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //일정 삭제
    @DeleteMapping("/api/schedules/{scheduleId}")
    public ResponseEntity<Void> handlerDeleteSchedule(
            @PathVariable Long scheduleId, HttpSession session
    ) {
        SessionUserDTO sessionUserDTO = (SessionUserDTO) session.getAttribute("loginUser");

        scheduleService.deleteSchedule(sessionUserDTO.getId(), scheduleId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //페이징 된 전체 일정 조회
    @GetMapping("/schedules/all")
    public ResponseEntity<Page<PagedScheduleResponse>> handlerGetPagedSchedule(
            @RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "0") int pageSize
    ) {
        Page<PagedScheduleResponse> result = scheduleService.getPagedSchedule(pageNo, pageSize);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
