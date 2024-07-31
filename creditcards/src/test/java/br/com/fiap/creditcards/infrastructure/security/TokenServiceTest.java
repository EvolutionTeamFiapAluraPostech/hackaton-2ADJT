package br.com.fiap.creditcards.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.creditcards.domain.entity.User;
import java.time.LocalDateTime;
import java.time.ZoneId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TokenServiceTest {

  public static final String DEFAULT_USER_ID = "6b42b26f-ffd8-4dc3-9caf-8629add26a92";
  public static final String DEFAULT_ISS = "API FIAP";
  public static final String DEFAULT_SUB = "Morpheus";
  public static final String DEFAULT_SECRET = "secret";
  public static final String DEFAULT_SECRET_VALUE = "Fi@p-@lur@-2ADJT";
  private String accessToken;
  @InjectMocks
  private TokenService tokenService;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(tokenService, DEFAULT_SECRET, DEFAULT_SECRET_VALUE);
  }

  private static User createUser(String userId, String sub, String iss, Long longDateTime) {
    var dateTime = getEpochMilli();
    return new User(userId, sub, iss, dateTime);
  }

  private static long getEpochMilli() {
    return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  @Test
  void shouldGenerateToken() {
    var dateTime = getEpochMilli();
    var user = createUser(DEFAULT_USER_ID, DEFAULT_SUB, DEFAULT_ISS, dateTime);

    var token = tokenService.generateToken(user);

    assertThat(token).isNotNull().isNotEmpty();
  }

  @Test
  void shouldThrowExceptionWhenGenerateToken() {
    ReflectionTestUtils.setField(tokenService, "secret", "");
    var dateTime = getEpochMilli();
    var user = createUser(DEFAULT_USER_ID, DEFAULT_SUB, DEFAULT_ISS, dateTime);

    assertThatThrownBy(() -> tokenService.generateToken(user)).isInstanceOf(
        RuntimeException.class);
  }

  @Test
  void shouldGetUserFromToken() {
    var dateTime = getEpochMilli();
    var user = createUser(DEFAULT_USER_ID, DEFAULT_SUB, DEFAULT_ISS, dateTime);
    accessToken = tokenService.generateToken(user);

    var userRecovered = tokenService.getUserFrom(accessToken);

    assertThat(userRecovered).isNotNull();
  }

  @Test
  void shouldReturnNullWhenGetUserFromToken() {
    var dateTime = getEpochMilli();
    var user = createUser(DEFAULT_USER_ID, "", DEFAULT_ISS, dateTime);
    accessToken = tokenService.generateToken(user);

    var userRecovered = tokenService.getUserFrom(accessToken);

    assertThat(userRecovered).isNull();
  }

  @Test
  void shouldGetSubject() {
    var dateTime = getEpochMilli();
    var user = createUser(DEFAULT_USER_ID, DEFAULT_SUB, DEFAULT_ISS, dateTime);
    accessToken = tokenService.generateToken(user);

    var subject = tokenService.getSubject(accessToken);

    assertThat(subject).isNotNull().isNotEmpty();
  }

  @Test
  void shouldThrowExceptionWhenGetSubject() {
    var token = "";

    assertThatThrownBy(() -> tokenService.getSubject(token)).isInstanceOf(
        RuntimeException.class);
  }
}