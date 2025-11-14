package org.example.scheduler.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.scheduler.dto.user.*;
import org.example.scheduler.service.UserService;
import org.example.scheduler.util.exception.CustomException;
import org.example.scheduler.util.exception.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> handlerCreateUser(
            @Valid @RequestBody CreateUserRequest request
    ) {

        CreateUserResponse result = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<GetUserResponse> handlerGetUserById(
            @PathVariable Long userId
    ) {

        GetUserResponse result = userService.getUserById(userId);

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PatchMapping("/api/private/users/{userId}")
    public ResponseEntity<UpdateUserResponse> handlerUpdateUser(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRequest request,
            HttpSession session
    ) {
        SessionUserDTO sessionUserDTO = (SessionUserDTO) session.getAttribute("loginUser");
        if (!sessionUserDTO.getId().equals(userId)) {
            throw new CustomException(ErrorCode.INVALID_USER);
        }

        UpdateUserResponse result = userService.updateUser(userId, request);

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
