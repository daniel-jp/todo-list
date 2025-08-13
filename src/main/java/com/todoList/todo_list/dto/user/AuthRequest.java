package com.todoList.todo_list.dto.user;

import lombok.Builder;

@Builder
public record AuthRequest(String email, String password) {
}
