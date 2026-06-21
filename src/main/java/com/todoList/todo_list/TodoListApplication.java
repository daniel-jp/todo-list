package com.todoList.todo_list;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TodoListApplication {


    public static void main(String[] args) {
        SpringApplication.run(TodoListApplication.class, args);
    }

    /*implements CommandLineRunner
    @Override
    public void run(String... args) throws Exception {

    }


    @Bean
    public CommandLineRunner initAdmin(UserRepository userRepository,
                                       RoleRepository roleRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {

            // 1 - Garantir que roles existam
            Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                    .orElseGet(() -> roleRepository.save(new Role( "ROLE_ADMIN")));

            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseGet(() -> roleRepository.save(new Role( "ROLE_USER")));

            // 2 - Create admin user and user if it doesn't exist
            User admin = userRepository.findByEmail("admin@system.com")
                    .orElseGet(() -> {
                        User u = new User();
                        u.setEmail("admin@system.com");
                        u.setName("Admin");
                        u.setPassword(passwordEncoder.encode("admin12"));
                        u.setEnabled(true);
                        u.setLocked(false);
                        return userRepository.save(u);
                    });

            User user = userRepository.findByEmail("user@system.com")
                    .orElseGet(() -> {
                        User u2 = new User();
                        u2.setEmail("user@system.com");
                        u2.setName("User");
                        u2.setPassword(passwordEncoder.encode("user12"));
                        u2.setEnabled(false);
                        u2.setLocked(true);

                        return userRepository.save(u2);
                    });

            //ensure the list is not null.

            if (admin.getRoles() == null || user.getRoles()==null) {
                admin.setRoles(new ArrayList<>());
                user.setRoles(new ArrayList<>());
            }

            // 3 - Assign ADMIN role to admin user, if not already done
            if ( !user.getRoles().contains(userRole)|| !admin.getRoles().contains(adminRole)) {

                user.getRoles().add(userRole);
                admin.getRoles().add(adminRole);
                userRepository.saveAll(List.of(user,admin));
                System.out.println("✅ ADMIN & USER role assigned to user");
            }

        };
    }


     */

}