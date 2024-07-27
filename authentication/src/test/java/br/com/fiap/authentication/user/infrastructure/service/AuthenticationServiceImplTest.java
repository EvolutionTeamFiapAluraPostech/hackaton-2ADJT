package br.com.fiap.authentication.user.infrastructure.service;

import static br.com.fiap.authentication.shared.testData.user.UserTestData.DEFAULT_USER_USERNAME;
import static br.com.fiap.authentication.shared.testData.user.UserTestData.createUser;
import static br.com.fiap.authentication.user.domain.messages.UserMessages.USER_USERNAME_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.fiap.authentication.user.application.gateway.UserGateway;
import br.com.fiap.authentication.user.domain.exception.NoResultException;
import br.com.fiap.authentication.user.infrastructure.schema.UserSchema;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.FieldError;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

  @Mock
  private UserGateway userService;
  @InjectMocks
  private AuthenticationServiceImpl authenticationService;

  @Test
  void shouldLoadUserByUsername() {
    var user = createUser();
    when(userService.findByUsername(DEFAULT_USER_USERNAME)).thenReturn(user);

    var userDetails = authenticationService.loadUserByUsername(DEFAULT_USER_USERNAME);

    assertThat(userDetails).isNotNull().isInstanceOf(UserSchema.class);
    assertThat(userDetails.getUsername()).isEqualTo(DEFAULT_USER_USERNAME);
  }

  @Test
  void shouldThrowNoResultExceptionWhenUserWasNotFoundByUsername() {
    when(userService.findByUsername(DEFAULT_USER_USERNAME)).thenThrow(
        new NoResultException(new FieldError(this.getClass().getSimpleName(), "username",
            USER_USERNAME_NOT_FOUND.formatted(DEFAULT_USER_USERNAME))));

    assertThatThrownBy(() -> authenticationService.loadUserByUsername(DEFAULT_USER_USERNAME))
        .isInstanceOf(NoResultException.class)
        .hasMessage(USER_USERNAME_NOT_FOUND.formatted(DEFAULT_USER_USERNAME));
  }
}
