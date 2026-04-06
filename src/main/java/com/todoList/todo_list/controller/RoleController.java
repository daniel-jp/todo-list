package com.todoList.todo_list.controller;

import com.todoList.todo_list.dto.user.RoleResponseDTO;
import com.todoList.todo_list.dto.user.RoleUpdateRequestDTO;
import com.todoList.todo_list.entity.Role;
import com.todoList.todo_list.entity.User;
import com.todoList.todo_list.service.role.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;

@CrossOrigin(origins = "*")
@RestController
//@CrossOrigin("*")
@RequestMapping("/api/v1/roles")
public class RoleController {


    @Autowired
    private RoleService roleService;


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping()
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        return new ResponseEntity<>(roleService.createRole(role), CREATED);
    }

    @GetMapping("/name")
    public ResponseEntity<Role> findByName(@RequestParam("name") String name) {
        return ResponseEntity.ok(roleService.findByName(name));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{roleId}")
    public ResponseEntity<RoleResponseDTO> updateRole(
            @PathVariable UUID roleId,
            @RequestBody @Valid RoleUpdateRequestDTO request) {
        return ResponseEntity.ok(roleService.updateRole(roleId, request));
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") UUID roleId){
        roleService.deleteRoleById(roleId);
        return ResponseEntity.ok("Role removed successful ✅");
    }

    //Olny ADMIN has the possibility to delete all users-from-role by roleId
    @DeleteMapping("/{id}/users")
    public ResponseEntity<String> removeRoleFromAllUsers(@PathVariable("id") UUID roleId){
         roleService.removeRoleFromAllUsers(roleId);
         return  ResponseEntity.ok("Role "+roleId+" successful deleted in all users ✅");
    }

    @DeleteMapping("/remove-role-from-user")
    public User removeRoleFromUser(@RequestParam("userId") UUID userId,
                                   @RequestParam("roleId") UUID roleId){
        return roleService.removeRoleFromUser(userId, roleId);
    }
    @PostMapping("/assign-user-to-role")
    public  User assignUserToRole(@RequestParam("userId") UUID userId,
                                  @RequestParam("roleId") UUID roleId){
        return roleService.assignUerToRole(userId, roleId);
    }
}
