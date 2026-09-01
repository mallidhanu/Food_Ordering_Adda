package com.food.food_adda.service;

import com.food.food_adda.dto.LoginRequest;
import com.food.food_adda.dto.LoginResponse;
import com.food.food_adda.dto.UserRegistrationRequest;
import com.food.food_adda.entity.User;
import com.food.food_adda.repository.UserRepository;
import com.food.food_adda.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public User registerUser(UserRegistrationRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        user.setRole(User.UserRole.CUSTOMER);
        user.setIsActive(true);

        return userRepository.save(user);
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        if (!user.getIsActive()) {
            throw new RuntimeException("User account is inactive");
        }

        String token = jwtTokenProvider.generateTokenWithRole(user.getEmail(), user.getRole().toString());

        LoginResponse response = new LoginResponse();
        response.setToken(token);
        response.setEmail(user.getEmail());
        response.setFullName(user.getFullName());
        response.setRole(user.getRole().toString());

        return response;
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long userId, User userDetails) {
        User user = getUserById(userId);
        user.setFullName(userDetails.getFullName());
        user.setPhone(userDetails.getPhone());
        return userRepository.save(user);
    }
}
