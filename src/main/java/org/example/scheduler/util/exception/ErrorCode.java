package org.example.scheduler.util.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    //== 200 ==//
    SUCCESS(HttpStatus.OK, "OK"),

    //== 400 ==//
    INVALID_PASSWORD(HttpStatus.NOT_FOUND, "유효하지 않은 패스워드 입니다."),
    SCHEDULE_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 일정입니다."),
    COMMENT_OUT_OF_BOUND(HttpStatus.BAD_REQUEST, "작성가능한 댓글 개수가 초과 되었습니다."),
    INVALID_USER(HttpStatus.UNAUTHORIZED, "유효하지 않은 사용자입니다."),
    ALREADY_LOGGED_IN(HttpStatus.CONFLICT,"이미 로그인 되어 있습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저 입니다."),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 댓글 입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"로그인 되어있지 않습니다.");


    private final HttpStatus status;
    private final String message;

}