package org.example.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduler.dto.comment.*;
import org.example.scheduler.dto.user.SessionUserDTO;
import org.example.scheduler.entity.Comment;
import org.example.scheduler.entity.Schedule;
import org.example.scheduler.entity.User;
import org.example.scheduler.repository.CommentRepository;
import org.example.scheduler.util.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final Validator validator;

    @Transactional
    public CommentResponse createComment(SessionUserDTO sessionUserDTO, Long scheduleId, CreateCommentRequest request) {
        User currentUser = validator.findUserByIdOrThrow(sessionUserDTO.getId());

        Schedule currentSchedule = validator.existScheduleById(scheduleId);

        Comment comment = new Comment(request.getContent(), currentUser, currentSchedule);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponse(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long scheduleId) {
        List<Comment> foundComments = commentRepository.findCommentsBySchedule_Id(scheduleId);

        return foundComments.stream()
                .map(CommentResponse::new)
                .toList();
    }

    @Transactional
    public CommentResponse modifyContent(UpdateCommentRequest request) {
        Comment comment = validator.getCommentByIdOrThrow(request);

        comment.modify(request.getContent());

        return new CommentResponse(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        boolean existence = commentRepository.existsById(commentId);

        validator.validateCommentExists(existence);

        commentRepository.deleteById(commentId);
    }

    @Transactional(readOnly = true)
    public Page<PagedCommentDTO> getPagedComment(Long scheduleId, int pageNo) {
        Page<Comment> pagedComments = commentRepository.findCommentsBySchedule_Id(scheduleId, Pageable.ofSize(10).withPage(pageNo));

        return pagedComments.map(PagedCommentDTO::new);
    }
}
