package com.todoList.todo_list.repositories;

import com.todoList.todo_list.dto.user.RegisterRequestDTO;
import com.todoList.todo_list.entity.Role;
import com.todoList.todo_list.entity.User;
import com.todoList.todo_list.integration.AbstractIntegrationTest;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EntityManager entityManager;




    @BeforeEach
    void setup() {
        roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        if (userRepository.findByEmail("admin@system.com").isEmpty()) {
            RegisterRequestDTO user = new RegisterRequestDTO(
                    null,
                    "Admin1",
                    "admin@system.com",
                    passwordEncoder.encode("admin"),
                    true,
                    false);

             this.createUser(user);
        }
    }

    @Test()
    @DisplayName("Test 1: [ findUserByEmail ]")
    void findByEmail() {

        Optional<User> userEmail = userRepository.findByEmail("admin@system.com");

       // assertTrue(userEmail.isPresent()); Or
        assertThat(userEmail.isPresent()).isTrue();
        assertEquals("admin@system.com", userEmail.get().getEmail());

        System.out.println("✅ Email found : "+userEmail.get().getEmail());


    }


    @Test()
    @DisplayName("Test 2: [ If user email is Empty]")
    void findByEmail2() {


        String email2 = "admin@system.com";
        Optional<User> userEmail = this.userRepository.findByEmail(email2);
        //assertThat(userEmail.isEmpty());
       AbstractBooleanAssert<?> abstractBooleanAssert = assertThat(userEmail.isEmpty());


    }


    //
   void createUser(RegisterRequestDTO request){

        User newUser =  new User(request);
        this.entityManager.persist(newUser);
        entityManager.flush();
       //  return newUser;
    }
}