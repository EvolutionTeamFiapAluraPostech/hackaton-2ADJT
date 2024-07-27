package br.com.fiap.authentication.user.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.authentication.shared.testData.user.UserTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

  @InjectMocks
  private TokenService tokenService;

  @Test
  void shouldGenerateToken() {
    ReflectionTestUtils.setField(tokenService, "secret", "Fi@p-@lur@-2ADJT");
    var userSchema = UserTestData.createUserSchema();

    var token = tokenService.generateToken(userSchema);

    assertThat(token).isNotNull().isNotEmpty();
  }

  @Test
  void shouldThrowExceptionWhenGenerateToken() {
    var userSchema = UserTestData.createUserSchema();

    assertThatThrownBy(() -> tokenService.generateToken(userSchema)).isInstanceOf(
        RuntimeException.class);
  }

  @Test
  void shouldGetSubject() {
    ReflectionTestUtils.setField(tokenService, "secret", "Fi@p-@lur@-2ADJT");
    var userSchema = UserTestData.createUserSchema();

    var token = tokenService.generateToken(userSchema);
    var subject = tokenService.getSubject(token);

    assertThat(subject).isNotNull().isNotEmpty();
  }

  @Test
  void shouldThrowExceptionWhenGetSubject() {
    var token = "";

    assertThatThrownBy(() -> tokenService.getSubject(token)).isInstanceOf(
        RuntimeException.class);
  }
}
