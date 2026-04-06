package com.todoList.todo_list.service;

import com.todoList.todo_list.dto.task.TaskDTO;
import com.todoList.todo_list.dto.task.TaskRequest;
import com.todoList.todo_list.enums.TaskStatus;
import com.todoList.todo_list.exception.TaskNotFoundException;
import com.todoList.todo_list.exception.UserNotFoundException;
import com.todoList.todo_list.mapper.TaskMapper;
import com.todoList.todo_list.repositories.TaskRepository;
import com.todoList.todo_list.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private TaskMapper mapper;

    // ✅ Criar Tarefa
    public TaskDTO createTask(TaskRequest request) {

        var user = userRepo.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var task = mapper.toTask(request, user);
        
        return mapper.toDTO(taskRepo.save(task));
    }

    // ✅ Buscar todas
    public List<TaskDTO> listAllTask() {
        return mapper.toDTOList(taskRepo.findAll());
    }

    // ✅ Buscar por usuário
    public List<TaskDTO> listByUserId(UUID userId) {
        return taskRepo.findByUserId(userId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    // ✅ Buscar tarefa por ID
    public TaskDTO findById(UUID id) {
        return taskRepo.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() ->
                        new TaskNotFoundException("Task not found ❌"));
    }


    // ✅ Atualizar tarefa
    public TaskDTO updateTask(UUID id, TaskRequest request) {

        var user = userRepo.findById(request.userId());

        if (user == null ||user.isEmpty() || !user.isPresent()) {
           throw  new UserNotFoundException("User not found ❌");
        }


        return taskRepo.findById(id)
                .map(task -> {
                    task.setTitle(request.title());
                    task.setDescription(request.description());
                   // task.setStatus(request.status());
                   // task.setDoneDate(request.doneDate());
                    task.setUser(user.get());
                    return mapper.toDTO(taskRepo.save(task));
                })
                .orElseThrow(() -> new TaskNotFoundException("Task not found ❌"));
    }

/*

    public TaskDTO toggleTaskStatus(UUID id) {
        return taskRepo.findById(id)
                .map(task -> {

                    if (task.getStatus() == TaskStatus.PENDING) {
                        task.setStatus(TaskStatus.PROCESSING);
                    }
                    else if (task.getStatus() == TaskStatus.PROCESSING) {
                        task.setStatus(TaskStatus.COMPLETED);
                        task.setDoneDate(new Date());
                    }
                    else if (task.getStatus() == TaskStatus.COMPLETED) {
                        task.setStatus(TaskStatus.PROCESSING);
                        task.setDoneDate(null);
                    }

                    return mapper.toDTO(taskRepo.save(task));
                })
                .orElseThrow(() -> new TaskNotFoundException("Task not found ❌"));
    }


 */

    public TaskDTO toggleTaskStatus(UUID id) {
        return taskRepo.findById(id)
                .map(task -> {
                    switch (task.getStatus()) {
                        case PENDING -> task.setStatus(TaskStatus.PROCESSING);
                        case PROCESSING -> {
                            task.setStatus(TaskStatus.COMPLETED);
                            task.setDoneDate(new Date());
                        }
                        case COMPLETED -> {
                            task.setStatus(TaskStatus.PENDING);
                            task.setDoneDate(null);
                        }
                    }
                    return mapper.toDTO(taskRepo.save(task));
                })
                .orElseThrow(() -> new TaskNotFoundException("Task not found ❌"));
    }

    // ✅ Deletar tarefa
    public void delete(UUID id) {
        if (!taskRepo.existsById(id)) {
            throw new TaskNotFoundException("Task not found  ❌");
        }
        taskRepo.deleteById(id);
    }

}
