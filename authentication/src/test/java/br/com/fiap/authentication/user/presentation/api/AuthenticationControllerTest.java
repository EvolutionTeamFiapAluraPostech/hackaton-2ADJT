package br.com.fiap.authentication.user.presentation.api;

import static br.com.fiap.authentication.shared.testData.user.UserTestData.DEFAULT_USER_PASSWORD;
import static br.com.fiap.authentication.shared.testData.user.UserTestData.DEFAULT_USER_USERNAME;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.authentication.shared.annotation.DatabaseTest;
import br.com.fiap.authentication.shared.annotation.IntegrationTest;
import br.com.fiap.authentication.shared.api.JsonUtil;
import br.com.fiap.authentication.shared.testData.user.UserTestData;
import br.com.fiap.authentication.user.presentation.dto.AuthenticateInputDto;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class AuthenticationControllerTest {

  private static final String URL_AUTHENTICATE = "/api/autenticacao";
  public static final String DMIN_1234 = "@Dmin1234";
  private final PasswordEncoder passwordEncoder;
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  AuthenticationControllerTest(PasswordEncoder passwordEncoder, MockMvc mockMvc,
      EntityManager entityManager) {
    this.passwordEncoder = passwordEncoder;
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  private void createAndPersistUser() {
    var userSchema = UserTestData.createUserSchema();
    var passwordEncoded = passwordEncoder.encode(userSchema.getPassword());
    userSchema.setPassword(passwordEncoded);
    entityManager.merge(userSchema);
  }

  @BeforeEach
  void setUp() {
    createAndPersistUser();
  }

  @Test
  void shouldAuthenticateUserWhenUserEmailAndPasswordIsValid() throws Exception {
    var authenticateInputDto = new AuthenticateInputDto(DEFAULT_USER_USERNAME,
        DEFAULT_USER_PASSWORD);
    var authenticateInputDtoJson = JsonUtil.toJson(authenticateInputDto);

    var request = post(URL_AUTHENTICATE)
        .contentType(APPLICATION_JSON)
        .content(authenticateInputDtoJson);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.token").isNotEmpty())
        .andExpect(jsonPath("$.token").isString()).andReturn();
  }

  @Test
  void shouldReturnUnauthorizedWhenUserIsNotValidToSignIn() throws Exception {
    var authenticateInputDto = new AuthenticateInputDto(DEFAULT_USER_USERNAME, DMIN_1234);
    var authenticateInputDtoJson = JsonUtil.toJson(authenticateInputDto);

    var request = post(URL_AUTHENTICATE)
        .contentType(APPLICATION_JSON)
        .content(authenticateInputDtoJson);
    mockMvc.perform(request)
        .andExpect(status().isUnauthorized())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.token").doesNotExist());
  }
}