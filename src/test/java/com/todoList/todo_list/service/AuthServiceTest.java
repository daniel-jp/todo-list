package com.todoList.todo_list.service;

import com.todoList.todo_list.dto.user.AuthRequest;
import com.todoList.todo_list.dto.user.AuthResponseDTO;
import com.todoList.todo_list.dto.user.RegisterRequestDTO;
import com.todoList.todo_list.dto.user.UserDTO;
import com.todoList.todo_list.entity.Role;
import com.todoList.todo_list.exception.UserAlreadyExistException;
import com.todoList.todo_list.integration.AbstractIntegrationTest;
import com.todoList.todo_list.repositories.RoleRepository;
import com.todoList.todo_list.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class AuthServiceTest extends AbstractIntegrationTest {

        @Autowired
        private AuthService authService;

        @Autowired
        private RoleRepository roleRepository;

        @Autowired
        private UserRepository userRepository;
        private RegisterRequestDTO registerRequest;


        @BeforeEach
        void setup() {
            // garante que a role existe
            roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

                registerRequest = new RegisterRequestDTO(
                         null,
                        "Daniel",
                        "admin@system.com",
                        "admin",
                        true,
                        false
                );

               // authService.createUser(registerRequest);
                // se já existir usuário no DB, apaga antes pra evitar conflito
                userRepository.findByEmail("admin@system.com")
                        .ifPresent(userRepository::delete);
        }


        @Test
            @DisplayName("Test 1- Should create a user successfully")
            void createUser_success() {
                UserDTO created = authService.createUser(registerRequest);

                assertThat(created).isNotNull();
                assertThat(created.email()).isEqualTo("admin@system.com");
                assertThat(userRepository.findByEmail("admin@system.com")).isPresent();

                System.out.println("User ["+created.name()+ "]  successfully created ✅");
            }

        @Test
        @DisplayName("Test 2- Should throw an exception when trying to create a duplicate user")
        void createUser_duplicateEmail() {

            authService.createUser(registerRequest);

            assertThatThrownBy(() -> authService.createUser(registerRequest))
                    .isInstanceOf(UserAlreadyExistException.class) // or UserAlreadyExistException
                    .hasMessageContaining("already exists");

            System.out.println("User already exists ⚠️");
        }

        @Test
        @DisplayName("Test 3- Should log in successfully and return JWT")
        void login_success() {
            // cria usuário antes de logar
            authService.createUser(registerRequest);

            AuthRequest loginRequest = new AuthRequest("admin@system.com","admin");
            AuthResponseDTO response = authService.login(loginRequest);

            assertThat(response).isNotNull();
            assertThat(response.token()).isNotBlank(); // return false, we can't see the token

            System.out.println("✅Token generate  : [ "+response.token() +" ]");
        }


        @Test
        @DisplayName("Test 4- Should fail login with incorrect password")
        void login_incorrectPassword() {

            authService.createUser(registerRequest);

            AuthRequest loginRequest = new AuthRequest("admin@system.com", "admin23");

                assertThatThrownBy(() -> authService.login(loginRequest))
                        .isInstanceOf(BadCredentialsException.class) // or BadCredentialsException
                        .hasMessageContaining("Incorrect password ❌");

            System.out.println("Incorrect password ❌");

        }

        @Test
        @DisplayName("Test 5- Should fail login with invalid email and password")
        void login_invalidEmail() {

            AuthRequest loginRequest = new AuthRequest("admin123@system.com", "admin123");

                assertThatThrownBy(() -> authService.login(loginRequest))
                        .isInstanceOf(UsernameNotFoundException.class) // ou UsernameNotFoundException
                        .hasMessageContaining("Invalid email");

            System.out.println("Invalid email or password");

        }



}