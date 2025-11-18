package org.example.scheduler.util;

import lombok.RequiredArgsConstructor;
import org.example.scheduler.schedule.entity.Schedule;
import org.example.scheduler.util.exception.CustomException;
import org.example.scheduler.util.exception.ErrorCode;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Validator {
    public void validateScheduleOwner(Long userId, Schedule foundSchedule) {
        if (!foundSchedule.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }
    }

    public void checkLoginUser(boolean login) {
        if (login) {
            throw new CustomException(ErrorCode.ALREADY_EXIST_IN);
        }
    }

    public void validateUserExists(boolean existence) {
        if (!existence) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
    }

    public void validateCommentExists(boolean existence) {
        if (!existence) {
            throw new CustomException(ErrorCode.COMMENT_NOT_FOUND);
        }
    }
}
