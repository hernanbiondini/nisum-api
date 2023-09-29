package com.nisum.user.api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nisum.user.api.dto.TokenResponseDTO;
import com.nisum.user.api.service.TokenService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/token")
@RequiredArgsConstructor
@Tag(name = "Token", description = "Api de Usuarios - token")
public class TokenController {

    private final TokenService tokenService;

    @GetMapping
    public TokenResponseDTO getToken() {
        return new TokenResponseDTO(tokenService.generateTokenBySecurityType("test@test.com"));
    }
}
