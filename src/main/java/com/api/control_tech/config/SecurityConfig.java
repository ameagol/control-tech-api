package com.api.control_tech.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@Component
@EnableMethodSecurity
@AllArgsConstructor
public class SecurityConfig {

    private UserDetailsService userDetailsService;

    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    private JwtAuthenticationFilter authenticationFilter;

    @Bean
    public static PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF for simplicity (if you're using JWT, ensure CSRF is unnecessary)
                .csrf(csrf -> csrf.disable())

                // Configure CORS to handle preflight requests and allow cross-origin requests
                .cors(Customizer.withDefaults())

                // Configure authorization rules
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**", "/api/subscribe").permitAll() // Open endpoints
                        .requestMatchers(toH2Console()).permitAll() // H2 Console access
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // Preflight requests
                        .anyRequest().authenticated() // Protect all other endpoints
                )

                // Allow H2 Console if necessary
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                // Set up basic authentication
                .httpBasic(Customizer.withDefaults())

                // Exception handling for 401 Unauthorized responses
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint) // Use custom entry point
                )

                // Add custom JWT filter
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
