package com.todoList.todo_list.service;

import com.todoList.todo_list.dto.user.AuthRequest;
import com.todoList.todo_list.dto.user.AuthResponseDTO;
import com.todoList.todo_list.dto.user.RegisterRequestDTO;
import com.todoList.todo_list.dto.user.UserDTO;
import com.todoList.todo_list.entity.User;
import com.todoList.todo_list.exception.UserAlreadyExistException;
import com.todoList.todo_list.exception.UserNotFoundException;
import com.todoList.todo_list.mapper.UserMapper;
import com.todoList.todo_list.repository.RoleRepository;
import com.todoList.todo_list.repository.UserRepository;
import com.todoList.todo_list.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired private UserRepository userRepo;
    @Autowired private UserMapper userMapper ;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private  RoleRepository roleRepository;



    public UserDTO createUser(RegisterRequestDTO request) {

        if (userRepo.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistException("The user email you are trying to create already exists ❗");
        }

        User user = userMapper.toUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        User savedUser = userRepo.save(user);
        return userMapper.fromUseDTO(savedUser);
    }


    public AuthResponseDTO login(AuthRequest authRequest) {

        User user = userRepo.findByEmail(authRequest.email())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email ❗"));

        if (!passwordEncoder.matches(authRequest.password(), user.getPassword())) {
            throw new BadCredentialsException("Incorrect password ❌");
        }

        String token = jwtUtil.generateToken(user);
        return new AuthResponseDTO(token);
    }




    public List<UserDTO> listAllUser() {
        return userMapper.toDTOList(userRepo.findAll());
    }


    public UserDTO getUserById(UUID id) {
        return userRepo.findById(id)
                .map(userMapper::fromUseDTO)
                .orElseThrow(() -> new UserNotFoundException("User does not founded ❌"));
    }

    public UserDTO updateUser(UUID id, RegisterRequestDTO request) {
        return userRepo.findById(id)
                .map(user ->{
                    //user.setId(request.useId());
                    user.setName(request.name());
                    return userMapper.fromUseDTO(userRepo.save(user));

                })
                .orElseThrow(() -> new UserNotFoundException("User does not founded ❌"));

    }

    public void deleteUser(UUID id) {

        if (userRepo.existsById(id)) {
            throw new UserNotFoundException("User does not founded ❌");
        }
        userRepo.deleteById(id);
    }
}
