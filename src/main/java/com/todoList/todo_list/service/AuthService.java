package com.todoList.todo_list.service;

import com.todoList.todo_list.dto.user.AuthRequest;
import com.todoList.todo_list.dto.user.AuthResponseDTO;
import com.todoList.todo_list.dto.user.RegisterRequestDTO;
import com.todoList.todo_list.dto.user.UserDTO;
import com.todoList.todo_list.entity.Role;
import com.todoList.todo_list.entity.User;
import com.todoList.todo_list.exception.UserAlreadyExistException;
import com.todoList.todo_list.exception.UserNotFoundException;
import com.todoList.todo_list.mapper.UserMapper;
import com.todoList.todo_list.repositories.RoleRepository;
import com.todoList.todo_list.repositories.UserRepository;
import com.todoList.todo_list.security.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired private UserRepository userRepo;
    @Autowired private UserMapper userMapper ;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtService jwtUtil;
    @Autowired private  RoleRepository roleRepository;

    @Autowired  AuthenticationManager authenticationManager;
    @Autowired  JwtService jwtService;

    public UserDTO createUser(RegisterRequestDTO request) {

        if (userRepo.findByEmail(request.email()).isPresent()) {
            throw new UserAlreadyExistException("The user email you are trying to create already exists ❗");
        }

        // 🔐 Sempre pegar ROLE_USER
        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() ->
                        new RuntimeException("ROLE_USER not found")
                );
        User user = userMapper.toUserEntity(request);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setEnabled(true);
        user.setLocked(false);

        // ✅ Associar SOMENTE ROLE_USER
        user.setRoles(List.of(roleUser));

        User savedUser = userRepo.save(user);
        return userMapper.fromUseDTO(savedUser);
    }


    public AuthResponseDTO login(AuthRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        User user = userRepo.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtService.generateToken(user);

        return new AuthResponseDTO(token, userMapper.fromUseDTO(user));
    }

 /*

    public AuthResponseDTO login(AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token, userMapper.fromUseDTO(user));
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

     */




    public List<UserDTO> listAllUser() {
        return userMapper.toDTOList(userRepo.findAll());
    }


    public UserDTO getUserById(UUID id) {
        return userRepo.findById(id)
                .map(userMapper::fromUseDTO)
                .orElseThrow(() -> new UserNotFoundException("User does not founded ❌"));
    }

    public UserDTO findByName(String name) {
        return userRepo.findByName(name)
                .map(userMapper::fromUseDTO)
                .orElseThrow(() -> new UserNotFoundException("User with name "+name+" does not founded"));
    }


    public UserDTO updateUser(UUID id, RegisterRequestDTO request) {
        return userRepo.findById(id)
                .map(user ->{
                   // user.setId(request.useId());
                    user.setName(request.name());
                    user.setEmail(request.email());
                    user.setEnabled(request.enabled());
                    user.setLocked(request.locked());


                    // 🔐 Atualizar roles somente se vierem
                    if (request.roles() != null && !request.roles().isEmpty()) {

                        List<Role> roles = roleRepository.findAllByNameIn(request.roles());

                        if (roles.size() != request.roles().size()) {
                            throw new RuntimeException("One or more roles are invalid");
                        }

                        user.setRoles(roles); // ✅ AGORA SIM
                    }

                    return userMapper.fromUseDTO(userRepo.save(user));

                })
                .orElseThrow(() -> new UserNotFoundException("User with this ID not founded"));

    }

    public void deleteUser(UUID id) {

        if (!userRepo.existsById(id)) {
            throw new UserNotFoundException("User does not founded ❌");
        }
        userRepo.deleteById(id);
    }

    
    public void enableUser(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(true);
        userRepo.save(user);
    }

    public void disableUser(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setEnabled(false);
        userRepo.save(user);
    }



    public void lockUser(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found ❌"));

       // user.setEnabled(true);
        user.setLocked(true);
        userRepo.save(user);
    }

    public void unlockUser(UUID userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found ❌"));

        //user.setEnabled(true);
        user.setLocked(false);
        userRepo.save(user);
    }
}
