package com.todoList.todo_list.dto.user;

import java.util.UUID;

public record RoleResponseDTO(
        UUID id,
        String name
) {
}
