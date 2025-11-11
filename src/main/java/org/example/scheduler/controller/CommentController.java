package org.example.scheduler.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduler.dto.comment.*;
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

    @GetMapping("/comments")
    public ResponseEntity<List<GetCommentResponse>> handlerGetComment(
            @RequestParam Long scheduleId
    ) {
        List<GetCommentResponse> result = commentService.getComments(scheduleId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/comments")
    public ResponseEntity<UpdateCommentResponse> handlerUpdateComment(
            @RequestBody UpdateCommentRequest request
            ) {
        UpdateCommentResponse result = commentService.modifyContent(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> handlerDelete(
            @PathVariable Long commentId
    ) {
        commentService.delete(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
