package br.com.fiap.authentication.user.presentation.api;

import br.com.fiap.authentication.user.infrastructure.schema.UserSchema;
import br.com.fiap.authentication.user.infrastructure.security.TokenService;
import br.com.fiap.authentication.user.presentation.dto.AuthenticateInputDto;
import br.com.fiap.authentication.user.presentation.dto.JwtAccessTokenDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autenticacao")
public class AuthenticationController implements AuthenticationInterface {

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;

  public AuthenticationController(
      AuthenticationManager authenticationManager,
      TokenService tokenService
  ) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  @PostMapping
  public ResponseEntity<?> authenticate(
      @RequestBody @Valid AuthenticateInputDto authenticateInputDto) {
    var authenticationToken = new UsernamePasswordAuthenticationToken(
        authenticateInputDto.usuario(),
        authenticateInputDto.senha());
    var authenticate = authenticationManager.authenticate(authenticationToken);
    var userSchema = ((UserSchema) authenticate.getPrincipal());
    var tokenJwt = tokenService.generateToken(userSchema.getUser());
    return ResponseEntity.ok(new JwtAccessTokenDto(tokenJwt));
  }
}
