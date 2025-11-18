package org.example.scheduler.user.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.user.service.UserService;
import org.example.scheduler.user.dto.CreateUserRequest;
import org.example.scheduler.user.dto.SessionUserDTO;
import org.example.scheduler.user.dto.UpdateUserRequest;
import org.example.scheduler.user.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserResponse> handlerCreateUser(
            @Valid @RequestBody CreateUserRequest request
    ) {

        UserResponse result = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserResponse> handlerGetUserById(
            @PathVariable Long userId
    ) {

        UserResponse result = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/api/users")
    public ResponseEntity<UserResponse> handlerUpdateUser(
            @Valid @RequestBody UpdateUserRequest request, HttpSession session
    ) {
        SessionUserDTO sessionUserDTO = (SessionUserDTO) session.getAttribute("loginUser");

        Long userId = sessionUserDTO.getId();

        UserResponse result = userService.updateUser(userId, request);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Void> handlerDeleteUser(
            @PathVariable Long userId
    ) {
        userService.deleteUser(userId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
