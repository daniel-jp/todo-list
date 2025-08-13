package com.todoList.todo_list.repository;

import com.todoList.todo_list.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    Optional<Role> findByName(String name);
    Role findRoleById(UUID id);
}
