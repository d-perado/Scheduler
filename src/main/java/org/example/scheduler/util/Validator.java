package org.example.scheduler.util;

import lombok.RequiredArgsConstructor;
import org.example.scheduler.dto.comment.UpdateCommentRequest;
import org.example.scheduler.entity.Comment;
import org.example.scheduler.entity.Schedule;
import org.example.scheduler.entity.User;
import org.example.scheduler.repository.CommentRepository;
import org.example.scheduler.repository.ScheduleRepository;
import org.example.scheduler.repository.UserRepository;
import org.example.scheduler.util.exception.CustomException;
import org.example.scheduler.util.exception.ErrorCode;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Validator {
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;

    public Schedule existScheduleById(Long scheduleId) {
        return scheduleRepository.findById(scheduleId).orElseThrow(()->new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
    }
    public void validateScheduleOwner(Long userId, Schedule findedSchedule) {
        if (!findedSchedule.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }
    }
    public User findUserByIdOrThrow(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void existUserByEmail(String email) {
        boolean existence = userRepository.existsByEmail(email);

        if (existence) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_IN);
        }
    }
    public void existUserById(Long userId) {
        boolean existence = userRepository.existsById(userId);

        if (!existence) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }
    public void validateCommentExists(boolean existence) {
        if (!existence) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }
    public Comment getCommentByIdOrThrow(UpdateCommentRequest request) {
        return commentRepository.findById(request.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public User findUserByEmailOrThrow(Optional<User> user) {
        return user
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
