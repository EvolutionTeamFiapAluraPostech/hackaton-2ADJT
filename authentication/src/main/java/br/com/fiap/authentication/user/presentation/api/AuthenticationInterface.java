package br.com.fiap.authentication.user.presentation.api;

import br.com.fiap.authentication.user.presentation.dto.AuthenticateInputDto;
import br.com.fiap.authentication.user.presentation.dto.JwtAccessTokenDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "AuthenticationApi", description = "API de autenticação no aplicativo fiap.authentication")
public interface AuthenticationInterface {

  @Operation(summary = "Autenticação de usuário do aplicativo",
      description = "Endpoint para usuários realizar autenticação com usuário e senha.",
      tags = {"AuthenticationApi"})
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "successful operation",
          content = {
              @Content(mediaType = "application/json", schema = @Schema(implementation = JwtAccessTokenDto.class))}),
      @ApiResponse(responseCode = "401", description = "unauthorized",
          content = {@Content(schema = @Schema(hidden = true))})})
  ResponseEntity<?> authenticate(AuthenticateInputDto authenticateInputDto);
}
