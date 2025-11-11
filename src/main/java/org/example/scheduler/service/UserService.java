package org.example.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduler.config.PasswordEncoder;
import org.example.scheduler.dto.login.LoginRequest;
import org.example.scheduler.dto.user.*;
import org.example.scheduler.entity.User;
import org.example.scheduler.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponse createUser(CreateUserRequest request) {
        boolean existence = userRepository.existsByEmail(request.getEmail());

        if (existence) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getName(),
                request.getEmail(),
                encodedPassword);

        User savedUser = userRepository.save(user);

        UserDTO savedUserDTO = new UserDTO(savedUser);

        return new CreateUserResponse(savedUserDTO);
    }

    @Transactional(readOnly = true)
    public GetUserResponse getUserById(Long userId) {
        User findedUser = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다."));

        UserDTO userDTO = new UserDTO(findedUser);

        return new GetUserResponse(userDTO);
    }

    @Transactional
    public UpdateUserResponse updateUser(Long userId, UpdateUserRequest request) {
        User findedUser = userRepository.findById(userId).orElseThrow(
                () -> new IllegalStateException("존재하지 않는 유저입니다."));

        findedUser.modify(request.getName(), request.getPassword());

        UserDTO userDTO = new UserDTO(findedUser);

        return new UpdateUserResponse(userDTO);
    }

    @Transactional
    public void deleteUser(Long userId) {
        boolean existence = userRepository.existsById(userId);

        if (!existence) {
            throw new IllegalStateException("존재하지 않는 유저입니다.");
        }

        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public SessionUserDTO login(LoginRequest request){
        User findedUser = userRepository.findUserByEmail(request.getEmail()).orElseThrow(
                ()-> new IllegalStateException("가입되지 않은 이메일입니다."));

        if(!passwordEncoder.matches(request.getPassword(),findedUser.getPassword())) {
            throw new IllegalArgumentException("패스워드가 일치하지 않습니다.");
        }

        return new SessionUserDTO(findedUser.getId(), findedUser.getEmail());
    }

    @Transactional(readOnly = true)
    public boolean validCommonUser(Long id) {
        return userRepository.existsById(id);
    }

}
