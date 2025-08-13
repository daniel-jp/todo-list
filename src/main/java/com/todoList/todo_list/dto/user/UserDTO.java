package com.todoList.todo_list.dto.user;

import com.todoList.todo_list.entity.Role;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

@Builder
public record UserDTO(
        UUID userId,
        String name,
        String email,
        String password,
        boolean enabled,
        boolean locked,
        List<Role> roles) {
}