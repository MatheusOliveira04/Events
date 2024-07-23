package com.eventos.api.controllers;

import com.eventos.api.config.jwt.JwtAuthFilter;
import com.eventos.api.config.jwt.JwtUtil;
import com.eventos.api.config.jwt.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping(value = "/auth")
@RestController
public class JwtController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager auth;

    @PostMapping("/token")
    public String authenticateAndGetToken(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = auth.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.email(), loginDTO.password()));
        if (authentication.isAuthenticated()) {
            return jwtUtil.generateToken(loginDTO.email());
        } else {
            throw new UsernameNotFoundException("Usuário inválido");
        }

    }
}
