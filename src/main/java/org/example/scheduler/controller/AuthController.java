package org.example.scheduler.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.dto.auth.LoginRequest;
import org.example.scheduler.dto.auth.LoginResponse;
import org.example.scheduler.dto.user.SessionUserDTO;
import org.example.scheduler.service.UserService;
import org.example.scheduler.util.exception.CustomException;
import org.example.scheduler.util.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/api/private/logout")
    public ResponseEntity<Void> handlerLogout(
            @SessionAttribute(name = "loginUser", required = false) SessionUserDTO sessionUser, HttpSession session) {
        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }
        session.invalidate();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> handlerLogin(
            @RequestBody LoginRequest request,
            HttpSession session
    ) {
        if (session.getAttribute("loginUser") != null) {
            throw new CustomException(ErrorCode.ALREADY_LOGGED_IN);
        }
        try {

            SessionUserDTO sessionUserDTO = userService.login(request);

            session.setAttribute("loginUser", sessionUserDTO);

            LoginResponse result = new LoginResponse(sessionUserDTO);

            return ResponseEntity.status(HttpStatus.OK).body(result);

        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
