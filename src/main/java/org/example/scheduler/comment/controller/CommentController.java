package org.example.scheduler.comment.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.comment.dto.CommentResponse;
import org.example.scheduler.comment.dto.CreateCommentRequest;
import org.example.scheduler.comment.dto.PagedCommentDTO;
import org.example.scheduler.comment.dto.UpdateCommentRequest;
import org.example.scheduler.user.dto.SessionUserDTO;
import org.example.scheduler.comment.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    //댓글 추가
    @PostMapping("/api/schedules/{scheduleId}/comments")
    public ResponseEntity<CommentResponse> handlerCreateComment(
            @PathVariable Long scheduleId, @Valid @RequestBody CreateCommentRequest request, HttpSession session
    ) {
        SessionUserDTO sessionUserDTO = (SessionUserDTO) session.getAttribute("loginUser");

        CommentResponse result = commentService.createComment(sessionUserDTO, scheduleId, request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    //댓글 조회
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponse>> handlerGetComment(
            @RequestParam Long scheduleId
    ) {

        List<CommentResponse> result = commentService.getComments(scheduleId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //댓글 수정
    @PatchMapping("/api/comments/{commentId}")
    public ResponseEntity<CommentResponse> handlerUpdateComment(
            @PathVariable Long commentId, @Valid @RequestBody UpdateCommentRequest request, HttpSession session
    ) {
        SessionUserDTO loginUser = (SessionUserDTO) session.getAttribute("loginUser");

        CommentResponse result = commentService.modifyContent(commentId, request, loginUser.getId());

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //댓글 삭제
    @DeleteMapping("/api/{commentId}")
    public ResponseEntity<Void> handlerDelete(
            @PathVariable Long commentId, HttpSession session
    ) {
        SessionUserDTO loginUser = (SessionUserDTO) session.getAttribute("loginUser");

        commentService.deleteComment(commentId, loginUser.getId());

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //특정 스케줄 댓글 조회
    @GetMapping("/comments/{scheduleId}")
    public ResponseEntity<Page<PagedCommentDTO>> handlerGetComments(
            @PathVariable Long scheduleId, @RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<PagedCommentDTO> result = commentService.getPagedComment(scheduleId, pageNo, pageSize);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
