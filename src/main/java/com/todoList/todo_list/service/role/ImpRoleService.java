package com.todoList.todo_list.service.role;

import com.todoList.todo_list.entity.Role;
import com.todoList.todo_list.entity.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

public interface ImpRoleService {
    List<Role> getAllRoles();
    Role createRole(Role theRole);
    ResponseEntity<String> deleteRole(UUID roleId);
    Role findByName(String name);
    Role findById(UUID roleId);
    User removeUserFromRole(UUID userId, UUID roleId);
    User assignUerToRole(UUID userId, UUID roleId);
    Role removeAllUserFromRole(UUID roleId);
}
