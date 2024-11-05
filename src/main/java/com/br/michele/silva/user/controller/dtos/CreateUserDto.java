package com.br.michele.silva.user.controller.dtos;

import com.br.michele.silva.user.enums.RoleName;

public record CreateUserDto(

        String email,
        String password,
        RoleName role

) {
}
