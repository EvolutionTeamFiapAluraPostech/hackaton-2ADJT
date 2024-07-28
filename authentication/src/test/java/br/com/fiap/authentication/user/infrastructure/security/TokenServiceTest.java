package br.com.fiap.authentication.user.infrastructure.security;

import static br.com.fiap.authentication.shared.testData.user.UserTestData.createUserSchema;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

  @InjectMocks
  private TokenService tokenService;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(tokenService, "secret", "Fi@p-@lur@-2ADJT");
  }

  @Test
  void shouldGenerateToken() {
    var userSchema = createUserSchema();
    var user = userSchema.getUser();

    var token = tokenService.generateToken(user);

    assertThat(token).isNotNull().isNotEmpty();
  }

  @Test
  void shouldThrowExceptionWhenGenerateToken() {
    ReflectionTestUtils.setField(tokenService, "secret", "");
    var userSchema = createUserSchema();
    var user = userSchema.getUser();

    assertThatThrownBy(() -> tokenService.generateToken(user)).isInstanceOf(
        RuntimeException.class);
  }

  @Test
  void shouldGetSubject() {
    var userSchema = createUserSchema();
    var user = userSchema.getUser();

    var token = tokenService.generateToken(user);
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
