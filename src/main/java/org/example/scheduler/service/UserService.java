package org.example.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduler.config.PasswordEncoder;
import org.example.scheduler.dto.auth.LoginRequest;
import org.example.scheduler.dto.user.*;
import org.example.scheduler.entity.Schedule;
import org.example.scheduler.entity.User;
import org.example.scheduler.repository.CommentRepository;
import org.example.scheduler.repository.ScheduleRepository;
import org.example.scheduler.repository.UserRepository;
import org.example.scheduler.util.Validator;
import org.example.scheduler.util.exception.CustomException;
import org.example.scheduler.util.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ScheduleRepository scheduleRepository;
    private final CommentRepository commentRepository;
    private final Validator validator;

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        validator.existUserByEmail(request.getEmail());

        User user = new User(request.getName(),
                request.getEmail(),
                request.getPassword(),
                passwordEncoder);

        User savedUser = userRepository.save(user);

        return new UserResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {
        User foundUser = validator.findUserByIdOrThrow(userId);

        return new UserResponse(foundUser);

    }

    @Transactional
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User foundUser = validator.findUserByIdOrThrow(userId);

        foundUser.modify(request.getName(), request.getPassword(),passwordEncoder);

        return new UserResponse(foundUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        validator.existUserById(userId);
        List<Schedule> foundSchedule = scheduleRepository.findSchedulesByUser_Id(userId);

        for (Schedule schedule:foundSchedule) {
            commentRepository.deleteAllBySchedule_Id(schedule.getId());
        }
        scheduleRepository.deleteAllByUser_Id(userId);

        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public SessionUserDTO login(LoginRequest request) {
        User foundUser = validator.findUserByEmailOrThrow(request.getEmail());

        if (!foundUser.isValid(request.getPassword(), passwordEncoder)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return new SessionUserDTO(foundUser.getId(), foundUser.getEmail());
    }

}