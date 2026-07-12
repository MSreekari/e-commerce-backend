package com.project.e_commerce.service;

import com.project.e_commerce.dto.auth.LoginRequest;
import com.project.e_commerce.dto.auth.LoginResponse;
import com.project.e_commerce.dto.auth.RegisterRequest;
import com.project.e_commerce.dto.auth.UserInfo;
import com.project.e_commerce.entity.User;
import com.project.e_commerce.repository.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    private User mapToEntity(RegisterRequest registerRequest){
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setRole("USER");
        return user;
    }

    private LoginResponse mapToResponse(User user) {
        LoginResponse response = new LoginResponse();
        response.setToken("dummy-token");
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setEmail(user.getEmail());
        userInfo.setRole(user.getRole());
        response.setUserInfo(userInfo);
        return response;
    }

    // add a new user
    public LoginResponse register(RegisterRequest registerRequest) {
        Optional<User> existingUser = userRepository.findByEmail(registerRequest.getEmail());
        if(existingUser.isPresent()){
            throw new EntityExistsException("user with this email already exists");
        }
        User user = mapToEntity(registerRequest);
        User newUser = userRepository.save(user);
        return mapToResponse(newUser);
    }

    // get all users
    public List<LoginResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapToResponse).toList();
    }

    // get user by id
    public LoginResponse getUserById(int id){
        User loginResponse = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with id " + id + " not found")
        );
        return mapToResponse(loginResponse);
    }

    // update user
    public LoginResponse updateUser(int id, LoginRequest loginRequest) {
        User existingUser = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with email " + loginRequest.getEmail() + " not found")
        );
        existingUser.setEmail(loginRequest.getEmail());
        existingUser.setPassword(loginRequest.getPassword());
        User updatedUser = userRepository.save(existingUser);
        return mapToResponse(updatedUser);
    }

    // delete a user
    public void deleteUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("User with " + id + " not found")
        );
        userRepository.delete(user);
    }

    // get user by email
    public LoginResponse getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("User with " + email + " not found"));

        return mapToResponse(user);
    }

    // login user
    public LoginResponse loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new EntityNotFoundException("Invalid email or password"));
        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }
        return mapToResponse(user);
    }
}
