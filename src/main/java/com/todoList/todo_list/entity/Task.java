package com.todoList.todo_list.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.todoList.todo_list.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date createdAt;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date doneDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
