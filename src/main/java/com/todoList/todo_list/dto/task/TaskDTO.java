package com.todoList.todo_list.dto.task;

import com.todoList.todo_list.entity.User;
import com.todoList.todo_list.enums.TaskStatus;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;

@Builder
public record TaskDTO(UUID taskId, String title, String description, TaskStatus status, Date createdAt, Date doneDate, User userId) {
}
