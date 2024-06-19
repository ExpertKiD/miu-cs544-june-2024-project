package edu.miu.attendance.security.controller;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("dev")
@RequestMapping("/api/v1")
public class SecurityDebugController {

    @GetMapping("/debug/roles")
    public String debugRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        StringBuilder roles = new StringBuilder("User roles: ");
        authentication.getAuthorities().forEach(authority -> {
            roles.append(authority.getAuthority()).append(" ");
        });

        if (authentication.isAuthenticated() && authentication.getPrincipal().getClass().equals(User.class)) {
            var principal = (User) authentication.getPrincipal();
        }

        return roles.toString();

    }
}
