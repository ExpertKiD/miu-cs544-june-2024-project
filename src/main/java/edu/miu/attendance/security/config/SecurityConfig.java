package edu.miu.attendance.security.config;

import edu.miu.attendance.domain.enums.RoleType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        String studentRole = RoleType.STUDENT.name();
        String[] adminRoles = new String[]{
                RoleType.FACULTY.name(),
                RoleType.STAFF.name(),
                RoleType.ADMIN.name()};
        String sysAdminRole = RoleType.ADMIN.name();


        http.authorizeHttpRequests(requests -> requests.requestMatchers("/api/v1/token").permitAll() // Allow access to
                                // the token endpoint without authentication
                                .requestMatchers("/api/v1/student-view/**").hasRole(studentRole) // Require STUDENT role for /api/v1/student-view/**
                                .requestMatchers("/api/v1/admin-view/**").hasAnyRole(adminRoles) // Require FACULTY, STAFF, or ADMIN adminRoles for /api/v1/admin-view/**
                                .requestMatchers("/api/v1/sys-admin/**").hasRole(sysAdminRole) // Require ADMIN role for /api/v1/sys-admin/**
                                .anyRequest().permitAll() // All other requests require
                        // authentication
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
