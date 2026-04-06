package com.todoList.todo_list.dto.user;

import jakarta.validation.constraints.NotBlank;

public record RoleUpdateRequestDTO(@NotBlank(message = "Role name is required")
                                   String name) {
}
