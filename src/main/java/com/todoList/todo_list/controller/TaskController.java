package com.todoList.todo_list.controller;

import com.todoList.todo_list.dto.task.TaskDTO;
import com.todoList.todo_list.dto.task.TaskRequest;
import com.todoList.todo_list.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*") // Opcional: libera o acesso CORS
@RequestMapping("/api/v1/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    // ✅ Criar tarefa
    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.createTask(request));
    }

    // ✅ Listar todas as tarefas
    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        return ResponseEntity.ok(taskService.listAllTask());
    }

    // ✅ Listar tarefas por ID do usuário
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskDTO>> getTasksByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(taskService.listByUserId(userId));
    }

    // ✅ Atualizar tarefa
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskDTO> updateTask(
            @PathVariable("taskId") UUID taskId,
            @RequestBody TaskRequest request) {
        return ResponseEntity.ok(taskService.updateTask(taskId, request));
    }

    // ✅ Marcar tarefa como concluída
    @PatchMapping("/{taskId}/complete")
    public ResponseEntity<String> completeTask(@PathVariable("taskId") UUID taskId) {

        return ResponseEntity.ok(taskService.completeTask(taskId));
    }

    // ✅ Deletar tarefa
    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable("taskId") UUID taskId) {

        taskService.delete(taskId);
        return ResponseEntity.ok("Task created successful ✅");
    }
}
