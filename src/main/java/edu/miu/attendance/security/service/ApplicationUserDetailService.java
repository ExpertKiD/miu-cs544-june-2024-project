package edu.miu.attendance.security.service;

import edu.miu.attendance.domain.Person;
import edu.miu.attendance.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ApplicationUserDetailService implements UserDetailsService {

    @Autowired
    private PersonRepository personRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Person user = personRepository.findPersonByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid " +
                        "username or password"));

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getRoles().forEach(role -> {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleType().name()));
        });

        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
