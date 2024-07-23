package com.eventos.api.config.jwt;

import com.eventos.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        com.eventos.api.models.user.User user = userRepository.findByEmail(email).orElseThrow(null);
        return User.builder()
                .username(user.getEmail())
                .password(encoder.encode(user.getPassword()))
                .roles(user.getRoles().split(","))
                .build();
    }
}
