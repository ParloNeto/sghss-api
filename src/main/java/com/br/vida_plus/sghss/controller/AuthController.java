package com.br.vida_plus.sghss.controller;

import com.br.vida_plus.sghss.dto.LoginDTO;
import com.br.vida_plus.sghss.model.RoleName;
import com.br.vida_plus.sghss.model.User;
import com.br.vida_plus.sghss.repository.UserRepository;
import com.br.vida_plus.sghss.request.AuthRequestDTO;
import com.br.vida_plus.sghss.request.JwtRequest;
import com.br.vida_plus.sghss.response.JwtResponse;
import com.br.vida_plus.sghss.service.JwtService;
import com.br.vida_plus.sghss.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {
        // decide se login é username ou email
        User user = userRepository.findByUsername(loginDTO.login())
                .or(() -> userRepository.findByEmail(loginDTO.login()))
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        if (!passwordEncoder.matches(loginDTO.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Senha inválida");
        }

        List<String> rolesAsString = user.getRoles()
                .stream()
                .map(Enum::name)
                .toList();

        String token = jwtService.generateToken(user.getUsername(), rolesAsString);

        return ResponseEntity.ok(Map.of("token", token));
    }



    @PostMapping("/register")
    public String register(@RequestBody AuthRequestDTO request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return "Usuário já existe!";
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return "Email já cadastrado!";
        }

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(RoleName.ROLE_PACIENTE))
                .build();

        userRepository.save(user);
        return "Usuário registrado com sucesso!";
    }
}

