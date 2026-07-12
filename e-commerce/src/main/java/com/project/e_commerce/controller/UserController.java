package com.project.e_commerce.controller;

import com.project.e_commerce.dto.auth.LoginRequest;
import com.project.e_commerce.dto.auth.LoginResponse;
import com.project.e_commerce.dto.auth.RegisterRequest;
import com.project.e_commerce.dto.product.ProductResponse;
import com.project.e_commerce.repository.UserRepository;
import com.project.e_commerce.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<LoginResponse> register(@RequestBody RegisterRequest registerRequest) {
        LoginResponse response = userService.register(registerRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<LoginResponse>> getAllUsers() {
        List<LoginResponse> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoginResponse> getUser(@PathVariable int id) {
        LoginResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoginResponse> updateUser(@PathVariable int id, @RequestBody LoginRequest loginRequest) {
        if(!userRepository.findById(id).isPresent()) {
            throw new RuntimeException("User not found");
        }
        LoginResponse user = userService.updateUser(id, loginRequest);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<LoginResponse> getUserByEmail(@PathVariable String email) {
        LoginResponse user = userService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser(@RequestBody LoginRequest loginRequest) {
        LoginResponse user = userService.loginUser(loginRequest);
        return ResponseEntity.ok(user);
    }
}
