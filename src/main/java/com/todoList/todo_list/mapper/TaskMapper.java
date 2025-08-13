package com.todoList.todo_list.mapper;

import com.todoList.todo_list.dto.task.TaskDTO;
import com.todoList.todo_list.dto.task.TaskRequest;
import com.todoList.todo_list.entity.Task;
import com.todoList.todo_list.entity.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class  TaskMapper {


    public Task toTask(TaskRequest request,  User user){

        if (request == null) {
            return null;
        }

        return Task.builder()
                //.id(request.taskId())
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .createdAt(new Date())
                .doneDate(request.doneDate())
                .user(user)
                .build();
    }

    public TaskDTO toDTO(Task task) {

        return new TaskDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getCreatedAt(),
                task.getDoneDate(),
                task.getUser()
        );
    }


    public List<TaskDTO> toDTOList(List<Task> tasks) {
        return tasks.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}