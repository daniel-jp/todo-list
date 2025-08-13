package com.todoList.todo_list.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ExceptionResponse implements Serializable {

    private Date timestamp;
    private String message;
    private String details;
}
