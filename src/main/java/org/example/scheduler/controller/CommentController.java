package org.example.scheduler.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduler.dto.comment.CreateCommentRequest;
import org.example.scheduler.dto.comment.CreateCommentResponse;
import org.example.scheduler.dto.comment.GetCommentResponse;
import org.example.scheduler.dto.schedule.GetScheduleResponse;
import org.example.scheduler.dto.user.SessionUserDTO;
import org.example.scheduler.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> handlerCreateComment(
            @PathVariable Long scheduleId,
            @RequestBody CreateCommentRequest request,
            @SessionAttribute(name = "loginUser", required = false) SessionUserDTO sessionUserDTO
    ) {

        CreateCommentResponse result = commentService.create(sessionUserDTO, scheduleId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/schedules/{scheduleId}/comments")
    public ResponseEntity<List<GetCommentResponse>> handlerGetComment(
            @PathVariable Long scheduleId
    ) {
        List<GetCommentResponse> result = commentService.getComments(scheduleId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
