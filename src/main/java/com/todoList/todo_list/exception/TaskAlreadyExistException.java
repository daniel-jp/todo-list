package com.todoList.todo_list.exception;

public class TaskAlreadyExistException extends RuntimeException {

    public TaskAlreadyExistException(String message) {
        super(message);
    }
}
