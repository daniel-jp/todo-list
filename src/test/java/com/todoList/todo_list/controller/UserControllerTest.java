package com.todoList.todo_list.controller;

import com.todoList.todo_list.integration.AbstractIntegrationTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

@AutoConfigureMockMvc
class UserControllerTest extends AbstractIntegrationTest {

    /*
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository repository;


    @Autowired
    private ObjectMapper objectMapper; // para converter objetos em JSON

    @BeforeEach
    void setup(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @DisplayName("Test 1 ➡ Should create a user successfully ✅")
    void createUser_success() throws Exception {

        // 🔥 garante que o user não existe
        repository.findByEmail("admin@system.com")
                .ifPresent(user -> repository.delete(user));

        RegisterRequestDTO request = new RegisterRequestDTO(
                null,
                "Daniel",
                "admin@system.com",
                "admin",
                true,
                false,
                null // roles opcional (depende do teu mapper)
        );

        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("User created successful ✅"));
    }


    @Test
    @DisplayName("Test 2 ➡ Should fail login with invalid credentials ❌")
    void login_fail_invalidCredentials() throws Exception {

        AuthRequest request = new AuthRequest("admin12@system.com", "admin22");

        ResultActions response = mockMvc.perform(post("/api/v1/users/auth")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        response.andExpect(status().is4xxClientError());
    }


    @Test
    @DisplayName("Test 3 ➡ Should fetch all users (GET /api/v1/users)")
    void getAllUsers() throws Exception {
        ResultActions response = mockMvc.perform(get("/api/v1/users")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test 4 ➡ Should return user by ID (GET /api/v1/users/{id})")
    void getUserById() throws Exception {
        UUID fakeId = UUID.randomUUID();

        ResultActions response = mockMvc.perform(get("/api/v1/users/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().is4xxClientError()); // vai dar 404 se não existir
    }

    @Test
    @DisplayName("Test 5 ➡ Should delete user (DELETE /api/v1/users/{id})")
    void deleteUserById() throws Exception {
        UUID fakeId = UUID.randomUUID();

        ResultActions response = mockMvc.perform(delete("/api/v1/users/" + fakeId)
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(status().is4xxClientError()); // vai dar 404 se não existir
    }

     */
}