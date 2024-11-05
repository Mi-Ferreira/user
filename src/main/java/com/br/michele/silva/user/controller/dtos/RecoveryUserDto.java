package com.br.michele.silva.user.controller.dtos;

import com.br.michele.silva.user.entities.Role;
import lombok.Builder;

import java.util.List;

@Builder
public class RecoveryUserDto {

    private Long id;
    private String email;
    private List<Role> roles;

}
