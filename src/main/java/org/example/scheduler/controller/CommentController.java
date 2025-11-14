package org.example.scheduler.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.dto.comment.*;
import org.example.scheduler.dto.user.SessionUserDTO;
import org.example.scheduler.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/api/private/schedules/{scheduleId}/comments")
    public ResponseEntity<CreateCommentResponse> handlerCreateComment(
            @PathVariable Long scheduleId,
            @RequestBody CreateCommentRequest request,
            HttpSession session
    ) {
        SessionUserDTO sessionUserDTO = (SessionUserDTO)session.getAttribute("loginUser");

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

    @PatchMapping("/api/private/comments")
    public ResponseEntity<UpdateCommentResponse> handlerUpdateComment(
            @RequestBody UpdateCommentRequest request
            ) {
        UpdateCommentResponse result = commentService.modifyContent(request);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/api/private/{commentId}")
    public ResponseEntity<Void> handlerDelete(
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/comments/{scheduleId}")
    public ResponseEntity<Page<PagedCommentDTO>> handlerGetComments(
            @PathVariable Long scheduleId,
            @RequestParam int pageNo
    ) {
        Page<PagedCommentDTO> result = commentService.getPagedComment(scheduleId, pageNo);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
