package org.example.scheduler.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduler.comment.dto.CommentResponse;
import org.example.scheduler.comment.dto.CreateCommentRequest;
import org.example.scheduler.comment.dto.PagedCommentDTO;
import org.example.scheduler.comment.dto.UpdateCommentRequest;
import org.example.scheduler.schedule.repository.ScheduleRepository;
import org.example.scheduler.user.dto.SessionUserDTO;
import org.example.scheduler.comment.entity.Comment;
import org.example.scheduler.schedule.entity.Schedule;
import org.example.scheduler.user.entity.User;
import org.example.scheduler.comment.repository.CommentRepository;
import org.example.scheduler.user.repository.UserRepository;
import org.example.scheduler.util.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final Validator validator;

    //댓글 추가
    @Transactional
    public CommentResponse createComment(SessionUserDTO sessionUserDTO, Long scheduleId, CreateCommentRequest request) {
        User currentUser = userRepository.findUserByIdOrThrow(sessionUserDTO.getId());

        Schedule currentSchedule = scheduleRepository.findScheduleByIdOrThrow(scheduleId);

        Comment comment = new Comment(request.getContent(), currentUser, currentSchedule);

        Comment savedComment = commentRepository.save(comment);

        return new CommentResponse(savedComment);
    }

    //댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(Long scheduleId) {
        List<Comment> foundComments = commentRepository.findCommentsWithUserAndSchedule(scheduleId);

        return foundComments.stream()
                .map(CommentResponse::new)
                .toList();
    }

    //댓글 수정
    @Transactional
    public CommentResponse modifyContent(Long commentId, UpdateCommentRequest request, Long loginUserId) {
        Comment comment = commentRepository.findCommentByIdOrThrow(commentId);

        validator.validateCommentOwner(loginUserId, comment);

        comment.modify(request.getContent());

        return new CommentResponse(comment);
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, Long loginUserId) {
        Comment comment = commentRepository.findCommentByIdOrThrow(commentId);

        validator.validateCommentOwner(loginUserId, comment);

        commentRepository.deleteById(commentId);
    }

    //특정 일정에 대한 댓글 페이징 조회
    @Transactional(readOnly = true)
    public Page<PagedCommentDTO> getPagedComment(Long scheduleId, int pageNo) {
        PageRequest pageRequest = PageRequest.of(pageNo, 10, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Comment> pagedComments = commentRepository.findCommentsBySchedule_Id(scheduleId, pageRequest);

        return pagedComments.map(PagedCommentDTO::new);

    }
}
