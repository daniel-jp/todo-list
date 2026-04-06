package com.todoList.todo_list.mapper;

import com.todoList.todo_list.dto.user.RegisterRequestDTO;
import com.todoList.todo_list.dto.user.UserDTO;
import com.todoList.todo_list.entity.User;
import com.todoList.todo_list.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
              //  .id(request.id() != null ? request.id() : UUID.randomUUID())
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .enabled(true)
                .locked(false)
                .roles(new ArrayList<>()) // 👈 inicializa vazio
                .build();
    }

    // Converter entidade User para DTO
    public UserDTO fromUseDTO(User user) {
        if (user == null) return null;

        return UserDTO.builder()
                .id(user.getId())
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
