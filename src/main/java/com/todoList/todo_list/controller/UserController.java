package com.todoList.todo_list.controller;

import com.todoList.todo_list.dto.user.AuthRequest;
import com.todoList.todo_list.dto.user.AuthResponseDTO;
import com.todoList.todo_list.dto.user.RegisterRequestDTO;
import com.todoList.todo_list.dto.user.UserDTO;
import com.todoList.todo_list.repositories.RoleRepository;
import com.todoList.todo_list.repositories.UserRepository;
import com.todoList.todo_list.security.jwt.JwtService;
import com.todoList.todo_list.service.AuthService;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private AuthService authService;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired
    @Resource
    private JwtService jwtUtil;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody RegisterRequestDTO registerRequestDTO){
        authService.createUser(registerRequestDTO);
        return  ResponseEntity.ok("User created successful ✅");
    }


    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

/*
    @PostMapping("/auth")
    public ResponseEntity<?> login(@RequestBody @Valid AuthRequest request) {
        try {
            return ResponseEntity.ok(authService.login(request));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
    }
 */




    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        return ResponseEntity.ok(authService.listAllUser());
    }

    @GetMapping("/{user_id}")
    public  ResponseEntity<UserDTO> getUserById(@PathVariable("user_id") UUID user_id){
        return ResponseEntity.ok(authService.getUserById(user_id));
    }

    @GetMapping("/user_name")
    public  ResponseEntity<UserDTO> getUserByName(@RequestParam(value = "user_name") String user_name ){
        return ResponseEntity.ok(authService.findByName(user_name));
    }

    /*
    @GetMapping("/{user_name}")
    public ResponseEntity<UserDTO> getUserByName(
            @PathVariable("user_name") String user_name
    ) {
        return ResponseEntity.ok(authService.findByName(user_name));
    }
     */

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("userId") UUID userId,
            @RequestBody RegisterRequestDTO request ){
        return ResponseEntity.ok(authService.updateUser(userId,request));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{user_id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("user_id") UUID user_id){
        authService.deleteUser(user_id);
        return ResponseEntity.ok("User deleted successful ✅");
    }



    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}/enable")
    public ResponseEntity<String > enableUser(@PathVariable UUID userId) {
        authService.enableUser(userId);
        return ResponseEntity.ok("User enabled successful ✅");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}/disable")
    public ResponseEntity<String> disableUser(@PathVariable UUID userId) {
        authService.disableUser(userId);
        return ResponseEntity.ok("User disabled  ❌");
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}/unlock")
    public ResponseEntity<String> unlockUser(@PathVariable UUID userId) {
        authService.unlockUser(userId);
        return ResponseEntity.ok("User unlocked 🔓");
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}/lock")
    public ResponseEntity<String> lockUser(@PathVariable UUID userId) {
        authService.lockUser(userId);
        return ResponseEntity.ok("User locked 🔒");
    }


}
