package com.todoList.todo_list;

import com.todoList.todo_list.entity.Role;
import com.todoList.todo_list.entity.User;
import com.todoList.todo_list.repository.RoleRepository;
import com.todoList.todo_list.repository.UserRepository;
import com.todoList.todo_list.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@EnableScheduling
public class TodoListApplication  implements CommandLineRunner {

	@Autowired
	private AuthService authService;

	public static void main(String[] args) {
		SpringApplication.run(TodoListApplication.class, args);
	}


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
						u.setName("Admin1");
						u.setPassword(passwordEncoder.encode("admin"));
						u.setEnabled(true);
						u.setLocked(false);
						return userRepository.save(u);
					});

			User user = userRepository.findByEmail("user@system.com")
					.orElseGet(() -> {
						User u2 = new User();
						u2.setEmail("user@system.com");
						u2.setName("User1");
						u2.setPassword(passwordEncoder.encode("user"));
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

				user.getRoles().add(userRole);admin.getRoles().add(adminRole);
				userRepository.saveAll(List.of(user,admin));
				System.out.println("✅ ADMIN & USER role assigned to user");
			}

		};
	}
}