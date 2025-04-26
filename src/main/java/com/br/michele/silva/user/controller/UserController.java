package com.br.michele.silva.user.controller;

import com.br.michele.silva.user.controller.dtos.CreateUserDto;
import com.br.michele.silva.user.controller.dtos.LoginUserDto;
import com.br.michele.silva.user.controller.dtos.RecoveryJwtTokenDto;
import com.br.michele.silva.user.controller.dtos.RecoveryUserDto;
import com.br.michele.silva.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/login")
    public RecoveryJwtTokenDto authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        return userService.authenticateUser(loginUserDto);
    }

    @PostMapping
    public void createUser(@RequestBody CreateUserDto createUserDto) {
        userService.createUser(createUserDto);
    }

    @GetMapping("/me")
    public RecoveryUserDto getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.replace("Bearer ", ""); // Remove o "Bearer " do começo;
        return userService.getUserByToken(token); // Recupera o usuário do banco de dados
    }

    @GetMapping("/test")
    @PreAuthorize("hasAnyRole(USER,ADMIN)")
    public String getAuthenticationTest() {
        return "Autenticado com sucesso";
    }

    @PreAuthorize("hasRole(USER)")
    @GetMapping("/test/customer")
    public String getCustomerAuthenticationTest() {
        return "Cliente autenticado com sucesso";
    }

    @PreAuthorize("hasRole(ADMIN)")
    @GetMapping("/test/administrator")
    public String getAdminAuthenticationTest() {
        return "Administrador autenticado com sucesso";
    }
}
