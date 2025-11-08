package org.example.scheduler.service;

import lombok.RequiredArgsConstructor;
import org.example.scheduler.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
}
