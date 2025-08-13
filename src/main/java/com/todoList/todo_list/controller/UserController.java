package com.todoList.todo_list.controller;

import com.todoList.todo_list.dto.user.AuthRequest;
import com.todoList.todo_list.dto.user.AuthResponseDTO;
import com.todoList.todo_list.dto.user.RegisterRequestDTO;
import com.todoList.todo_list.dto.user.UserDTO;
import com.todoList.todo_list.security.jwt.JwtUtil;
import com.todoList.todo_list.service.AuthService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private AuthService authService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired
    @Resource
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody RegisterRequestDTO registerRequestDTO){
        authService.createUser(registerRequestDTO);
        return  ResponseEntity.ok("User created successful ✅");
    }



    @PostMapping("/auth")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }



    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(authService.listAllUser());
    }

    @GetMapping("/{user_id}")
    public  ResponseEntity<UserDTO> getUserById(@PathVariable("user_id")UUID user_id){
        return ResponseEntity.ok(authService.getUserById(user_id));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("userId") UUID userId,
            @RequestBody RegisterRequestDTO request ){
        return ResponseEntity.ok(authService.updateUser(userId,request));
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("user_id") UUID user_id){
        authService.deleteUser(user_id);
        return ResponseEntity.ok("User deleted successful ✅");
    }

}
