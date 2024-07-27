package br.com.fiap.authentication.user.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "JwtAccessTokenDto", description = "DTO de representação de um token JWT")
public record JwtAccessTokenDto(
    @Schema(description = "JWT Access Token")
    String token
) {

}
