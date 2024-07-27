package br.com.fiap.authentication.user.infrastructure.service;

import br.com.fiap.authentication.user.application.gateway.UserGateway;
import br.com.fiap.authentication.user.infrastructure.schema.UserSchema;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements UserDetailsService {

  private final UserGateway userService;

  public AuthenticationServiceImpl(UserGateway userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    var user = userService.findByUsername(username);
    return UserSchema.builder()
        .id(user.getId())
        .username(user.getUsername())
        .password(user.getPassword().passwordValue())
        .build();
  }
}
