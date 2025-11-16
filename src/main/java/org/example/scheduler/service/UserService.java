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
import java.util.Optional;

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
        User findedUser = validator.findUserByEmailOrThrow(userRepository.findById(userId));

        return new UserResponse(findedUser);

    }

    @Transactional
    public UserResponse updateUser(Long userId, UpdateUserRequest request) {
        User findedUser = validator.findUserByEmailOrThrow(userRepository.findById(userId));

        findedUser.modify(request.getName(), request.getPassword());

        return new UserResponse(findedUser);
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
        User findedUser = validator.findUserByEmailOrThrow(userRepository.findUserByEmail(request.getEmail()));

        if (!findedUser.isValid(request.getPassword(), passwordEncoder)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return new SessionUserDTO(findedUser.getId(), findedUser.getEmail());
    }

}