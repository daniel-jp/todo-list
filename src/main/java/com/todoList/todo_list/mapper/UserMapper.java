package com.todoList.todo_list.mapper;

import com.todoList.todo_list.dto.user.RegisterRequestDTO;
import com.todoList.todo_list.dto.user.UserDTO;
import com.todoList.todo_list.entity.User;
import com.todoList.todo_list.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    @Autowired
    private RoleRepository roleRepository;

    // Convert DTO de registro para entidade User

    public User toUserEntity(RegisterRequestDTO request) {

        if (request == null) return null;



        return User.builder()
                //.id(request.userId() != null ? request.userId() : UUID.randomUUID())
                .name(request.name())
                .email(request.email())
                .password(request.password())
               // .roles((List<Role>)(request.roles())) // assumindo que `User.roles` é `List<Role>`
                .enabled(true)
                .locked(false)
                .build();
    }

    // Converter entidade User para DTO
    public UserDTO fromUseDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .userId(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .locked(user.isLocked())
                .roles(user.getRoles())
                .build();
    }

    // Lista de Users → Lista de DTOs
    public List<UserDTO> toDTOList(List<User> users) {
        return users.stream()
                .map(this::fromUseDTO)
                .collect(Collectors.toList());
    }
}
