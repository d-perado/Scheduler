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

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        boolean existence = userRepository.existsByEmail(request.getEmail());

        if (existence) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        User user = new User(request.getName(),
                request.getEmail(),
                request.getPassword(),
                passwordEncoder);

        User savedUser = userRepository.save(user);

        return new CreateUserResponse(savedUser);
    }

    @Transactional(readOnly = true)
    public GetUserResponse getUserById(Long userId) {
        User findedUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return new GetUserResponse(findedUser);

    }

    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request) {
        User findedUser = userRepository.findById(userId).orElseThrow(
                () -> new CustomException(ErrorCode.USER_NOT_FOUND));

        findedUser.modify(request.getName(), request.getPassword());

        return new UpdateUserResponse(findedUser);
    }

    @Transactional
    public void deleteUser(Long userId) {
        boolean existence = userRepository.existsById(userId);

        if (!existence) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }
        List<Schedule> foundSchedule = scheduleRepository.findSchedulesByUser_Id(userId);

        for (Schedule schedule:foundSchedule) {
            commentRepository.deleteAllBySchedule_Id(schedule.getId());
        }
        scheduleRepository.deleteAllByUser_Id(userId);

        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public SessionUserDTO login(LoginRequest request) {
        User findedUser = userRepository.findUserByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        if (!findedUser.isValid(request.getPassword(), passwordEncoder)) {
            throw new CustomException(ErrorCode.INVALID_PASSWORD);
        }

        return new SessionUserDTO(findedUser.getId(), findedUser.getEmail());
    }

}