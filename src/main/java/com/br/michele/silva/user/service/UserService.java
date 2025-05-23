package com.br.michele.silva.user.service;

import com.br.michele.silva.user.config.SecurityConfig;
import com.br.michele.silva.user.controller.dtos.CreateUserDto;
import com.br.michele.silva.user.controller.dtos.LoginUserDto;
import com.br.michele.silva.user.controller.dtos.RecoveryJwtTokenDto;
import com.br.michele.silva.user.controller.dtos.RecoveryUserDto;
import com.br.michele.silva.user.entities.Role;
import com.br.michele.silva.user.entities.User;
import com.br.michele.silva.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenService jwtTokenService;

    private final UserRepository userRepository;

    private final SecurityConfig securityConfiguration;

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    // Método responsável por criar um usuário
    public void createUser(CreateUserDto createUserDto) {

        // Cria um novo usuário com os dados fornecidos
        User newUser = User.builder()
                .email(createUserDto.email())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                // Atribui ao usuário uma permissão específica
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .build();

        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }

    public RecoveryUserDto getUserByToken(String token) {
        var userId = jwtTokenService.getUserIdFromToken(token);
        var user = userRepository.findById(userId);
        return RecoveryUserDto.builder()
                .id(user.orElseThrow().getId())
                .email(user.orElseThrow().getEmail())
                .roles(user.orElseThrow().getRoles())
                .build();

    }
}
