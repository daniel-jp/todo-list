package com.todoList.todo_list.controller;

import com.todoList.todo_list.entity.Role;
import com.todoList.todo_list.entity.User;
import com.todoList.todo_list.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.FOUND;

@RestController
//@CrossOrigin("*")
@RequestMapping("/api/v1/roles")
public class RoleController {


    @Autowired
    private RoleService roleService;


    @GetMapping()
    public ResponseEntity<List<Role>> getAllRoles(){
        return new ResponseEntity<>(roleService.getAllRoles(), FOUND);
    }
    @PostMapping()
    public ResponseEntity<Role> createRole(@RequestBody Role role){
        return new ResponseEntity<>(roleService.createRole(role), CREATED);
    }

    @GetMapping("/{name}")
    public Role findByName(@RequestParam("name") String name) {
        return roleService.findByName(name);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRole(@PathVariable("id") UUID roleId){
        roleService.deleteRole(roleId);
        return ResponseEntity.ok("Role removed successful ✅");
    }

    //Olny ADMIN has the possibility to delete all users-from-role by roleId
    @DeleteMapping("/{id}/users")
    public ResponseEntity<String> removeAllUsersFromRole(@PathVariable("id") UUID roleId){
         roleService.removeAllUserFromRole(roleId);
         return  ResponseEntity.ok("Role "+roleId+" successful deleted in all users ✅");
    }

    @DeleteMapping("/remove-user-from-role")
    public User removeUserFromRole(@RequestParam("userId") UUID userId,
                                   @RequestParam("roleId") UUID roleId){
        return roleService.removeUserFromRole(userId, roleId);
    }
    @PostMapping("/assign-user-to-role")
    public  User assignUserToRole(@RequestParam("userId") UUID userId,
                                  @RequestParam("roleId") UUID roleId){
        return roleService.assignUerToRole(userId, roleId);
    }
}
