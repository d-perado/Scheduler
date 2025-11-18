package org.example.scheduler.util;

import lombok.RequiredArgsConstructor;
import org.example.scheduler.comment.dto.UpdateCommentRequest;
import org.example.scheduler.comment.entity.Comment;
import org.example.scheduler.schedule.entity.Schedule;
import org.example.scheduler.user.entity.User;
import org.example.scheduler.comment.repository.CommentRepository;
import org.example.scheduler.schedule.repository.ScheduleRepository;
import org.example.scheduler.user.repository.UserRepository;
import org.example.scheduler.util.exception.CustomException;
import org.example.scheduler.util.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {

    public Schedule existScheduleById(Long scheduleId, ScheduleRepository scheduleRepository) {
        return scheduleRepository.findById(scheduleId).orElseThrow(() -> new CustomException(ErrorCode.SCHEDULE_NOT_FOUND));
    }

    public void validateScheduleOwner(Long userId, Schedule foundSchedule) {
        if (!foundSchedule.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }
    }

    public User findUserByIdOrThrow(Long userId, UserRepository userRepository) {
        return userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public void existUserByEmail(String email, UserRepository userRepository) {
        boolean existence = userRepository.existsByEmail(email);

        if (existence) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_IN);
        }
    }

    public void existUserById(Long userId, UserRepository userRepository) {
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

    public Comment getCommentByIdOrThrow(UpdateCommentRequest request, CommentRepository commentRepository) {
        return commentRepository.findById(request.getId()).orElseThrow(
                () -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
    }

    public User findUserByEmailOrThrow(String email, UserRepository userRepository) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }
}
