package com.todoList.todo_list.service.role;

import com.todoList.todo_list.entity.Role;
import com.todoList.todo_list.entity.User;
import com.todoList.todo_list.exception.UserAlreadyExistException;
import com.todoList.todo_list.exception.UserNotFoundException;
import com.todoList.todo_list.repository.RoleRepository;
import com.todoList.todo_list.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService implements ImpRoleService {
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;


    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }



    @Override
    public Role createRole(Role theRole) {

        theRole.setName(theRole.getName().toUpperCase());

        Optional<Role> checkRole = roleRepository.findByName(theRole.getName());
        if (checkRole.isPresent()){
            throw new UserAlreadyExistException("The User role with name "+checkRole.get().getName()+ " already exist ❗" +
                    " Please add a NEW role name ");
        }

        return roleRepository.save(theRole);
    }

    @Override
    public ResponseEntity<String> deleteRole(UUID roleId) {
        Optional<Role> optional =roleRepository.findById(roleId);
        if (optional.isEmpty()) {
            throw  new UserNotFoundException("The User id "+roleId+" Role to delete not exist ❌");
        }
        this.removeAllUserFromRole(roleId);
        roleRepository.deleteById(roleId);
        return ResponseEntity.ok().build();
    }
 /*
    @Override
    public Role findByName(String name) {
        Optional<Role> roleOptional = roleRepository.findByName(name);
        if (!roleOptional.isPresent()) {
            throw new UserNotFoundException("User Role with name "+roleOptional.get().getName()+" not exist!");
        }
        return roleRepository.findByName(name).get();
    }
 */

    @Override
    public Role findByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Role name must be provided ❗");
        }

        return roleRepository.findByName(name)
                .orElseThrow(() -> new UserNotFoundException("User Role with name " +name+ " not exist ❗"));
    }





    @Override
    public Role findById(UUID roleId) {

        var idr = roleRepository.findById(roleId);
        if (!idr.isPresent()) {
            throw new UserNotFoundException("User Role with name"+roleId+" not exist!");
        }
        return roleRepository.findById(roleId).get();
    }

    @Override
    public User removeUserFromRole(UUID userId, UUID roleId) {

        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);

        if (role.isPresent() && role.get().getUsers().contains(user.get())) {
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }
        throw new UserNotFoundException("User not found ❗");
    }

    @Override
    public User assignUerToRole(UUID userId, UUID roleId) {

        Optional<User> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);

        if (user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistException(
                    user.get().getEmail()+ "User a-mail is already assigned to the " + role.get().getName() +" role");
        }
        role.ifPresent(theRole -> theRole.assignUserToRole(user.get()));
        roleRepository.save(role.get());
        return user.get();

    }



    @Override
    public Role removeAllUserFromRole(UUID roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new UserNotFoundException("Role not found with id: " +roleId));

        // Remove todos os usuários
        role.removeAllUsersFromRole();
        return roleRepository.save(role);
    }


    /*
    @Override
    public Role removeAllUserFromRole(UUID roleId) {
        Optional<Role> role = roleRepository.findById(roleId);

        role.ifPresent(Role::removeAllUsersFromRole);
        return roleRepository.save(role.get());
    }
*/
}
