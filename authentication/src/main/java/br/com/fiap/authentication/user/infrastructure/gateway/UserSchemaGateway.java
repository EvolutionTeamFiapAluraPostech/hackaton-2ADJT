package br.com.fiap.authentication.user.infrastructure.gateway;

import static br.com.fiap.authentication.user.domain.messages.UserMessages.USER_USERNAME_NOT_FOUND;

import br.com.fiap.authentication.user.domain.exception.NoResultException;
import br.com.fiap.authentication.user.application.gateway.UserGateway;
import br.com.fiap.authentication.user.domain.entity.User;
import br.com.fiap.authentication.user.infrastructure.repository.UserSchemaRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class UserSchemaGateway implements UserGateway {

  private final UserSchemaRepository userRepository;

  public UserSchemaGateway(UserSchemaRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findByUsername(String username) {
    var userSchema = userRepository.findByUsername(username).orElseThrow(
        () -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "username",
            USER_USERNAME_NOT_FOUND.formatted(username))));
    return userSchema.getUser();
  }
}
