package org.example.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduler.dto.content.CommentDTO;
import org.example.scheduler.dto.content.CreateCommentRequest;
import org.example.scheduler.dto.content.CreateCommentResponse;
import org.example.scheduler.dto.user.SessionUserDTO;
import org.example.scheduler.entity.Comment;
import org.example.scheduler.entity.Schedule;
import org.example.scheduler.entity.User;
import org.example.scheduler.repository.CommentRepository;
import org.example.scheduler.repository.ScheduleRepository;
import org.example.scheduler.repository.UserRepository;
import org.example.scheduler.util.exception.CustomException;
import org.example.scheduler.util.exception.ErrorCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;


    public CreateCommentResponse create(SessionUserDTO sessionUserDTO, Long scheduleId, CreateCommentRequest request) {
        User currentUser = userRepository.findById(sessionUserDTO.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Schedule currentSchedule = scheduleRepository.findById(scheduleId).orElseThrow(
                () -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));

        Comment comment = new Comment(request.getContent(), currentUser,currentSchedule);

        CommentDTO commentDTO = new CommentDTO(comment);

        return new CreateCommentResponse(commentDTO);
    }
}
