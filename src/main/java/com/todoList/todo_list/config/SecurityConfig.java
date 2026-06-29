package com.todoList.todo_list.config;

import com.todoList.todo_list.security.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {



    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


       return http

               // ✅ ATIVA CORS (usa o bean abaixo)
               .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
               // .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth
                                .requestMatchers(HttpMethod.POST, "/api/v1/users/login/**").permitAll()
                                .requestMatchers(HttpMethod.GET,"/api/v1/tasks/**","/api/v1/tasks/**").permitAll()
                                .requestMatchers(HttpMethod.PUT,"/api/v1/tasks/**").permitAll()
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/tasks/**").permitAll()
                                .requestMatchers(HttpMethod.PATCH,"/api/v1/tasks/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/v1/users/**","/api/v1/tasks/**").permitAll()

                                .requestMatchers(HttpMethod.GET,
                                        "/api/v1/users/**",
                                        "/api/v1/roles/**","/api/v1/roles/{name}").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.POST, "/api/v1/roles/**").hasRole("ADMIN")
                                .requestMatchers(HttpMethod.PUT,"/api/v1/users/**",  "/api/v1/roles/**",
                                        "/api/v1/users/{userId}/lock", "/api/v1/users/{userId}/unlock",
                                        "/api/v1/users/{userId}/enable", "/api/v1/users/{userId}/disable").hasRole("ADMIN")

                                .requestMatchers(HttpMethod.DELETE,"/api/v1/roles/**","/api/v1/users/**").hasRole("ADMIN").anyRequest().authenticated()
                )
               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
        public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                throws Exception {
            return authenticationConfiguration.getAuthenticationManager();
        }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
