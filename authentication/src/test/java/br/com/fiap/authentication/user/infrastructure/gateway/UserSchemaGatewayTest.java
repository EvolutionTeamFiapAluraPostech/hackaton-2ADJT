package br.com.fiap.authentication.user.infrastructure.gateway;

import static br.com.fiap.authentication.shared.testData.user.UserTestData.DEFAULT_USER_USERNAME;
import static br.com.fiap.authentication.shared.testData.user.UserTestData.createUserSchema;
import static br.com.fiap.authentication.user.domain.messages.UserMessages.USER_USERNAME_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.fiap.authentication.user.domain.exception.NoResultException;
import br.com.fiap.authentication.user.infrastructure.repository.UserSchemaRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.FieldError;

@ExtendWith(MockitoExtension.class)
class UserSchemaGatewayTest {

  @Mock
  private UserSchemaRepository userRepository;
  @InjectMocks
  private UserSchemaGateway userSchemaGateway;

  @Test
  void shouldFindUserByUsername() {
    var userSchema = createUserSchema();
    when(userRepository.findByUsername(userSchema.getUsername())).thenReturn(
        Optional.of(userSchema));

    var user = userSchemaGateway.findByUsername(userSchema.getUsername());

    assertThat(user).isNotNull();
    assertThat(user.getId()).isNotNull().isEqualTo(userSchema.getId());
    assertThat(user.getUsername()).isNotNull().isEqualTo(userSchema.getUsername());
    assertThat(user.getPassword()).isNotNull();
    assertThat(user.getPassword().passwordValue()).isNotNull().isEqualTo(userSchema.getPassword());
  }

  @Test
  void shouldThrowNoResultExceptionWhenUserWasNotFoundByUsername() {
    when(userRepository.findByUsername(DEFAULT_USER_USERNAME)).thenThrow(
        new NoResultException(new FieldError(this.getClass().getSimpleName(), "username",
            USER_USERNAME_NOT_FOUND.formatted(DEFAULT_USER_USERNAME))));

    assertThatThrownBy(() -> userSchemaGateway.findByUsername(DEFAULT_USER_USERNAME))
        .isInstanceOf(NoResultException.class)
        .hasMessage(USER_USERNAME_NOT_FOUND.formatted(DEFAULT_USER_USERNAME));
  }
}
