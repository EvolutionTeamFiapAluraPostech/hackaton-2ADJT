package br.com.fiap.authentication.user.presentation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;

@Tag(name = "AuthenticateInputDto", description = "DTO de entrada autenticação do usuário")
public record AuthenticateInputDto(
    @Schema(example = "thomas.anderson", description = "Username do usuário")
    @NotBlank
    String usuario,
    @Schema(example = "@admin123", description = "Senha do usuário")
    @NotBlank
    String senha) {

}
