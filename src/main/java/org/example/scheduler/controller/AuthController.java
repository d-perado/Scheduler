package org.example.scheduler.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
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

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Void> handlerLogin(
            @Valid @RequestBody LoginRequest request,
            HttpSession session
    ) {
        if (session.getAttribute("loginUser") != null) {
            throw new CustomException(ErrorCode.ALREADY_LOGGED_IN);
        }
        SessionUserDTO sessionUserDTO = userService.login(request);

        session.setAttribute("loginUser", sessionUserDTO);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/api/logout")
    public ResponseEntity<Void> handlerLogout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
