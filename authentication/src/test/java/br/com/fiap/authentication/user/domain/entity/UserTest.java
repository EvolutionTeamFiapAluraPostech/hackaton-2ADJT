package br.com.fiap.authentication.user.domain.entity;

import static br.com.fiap.authentication.shared.testData.user.UserTestData.DEFAULT_USER_USERNAME;
import static br.com.fiap.authentication.shared.testData.user.UserTestData.DEFAULT_USER_PASSWORD;
import static org.junit.jupiter.api.Assertions.*;

import br.com.fiap.authentication.user.domain.exception.ValidatorException;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class UserTest {

  @Test
  void shouldCreateNewUser() {
    assertDoesNotThrow(() -> new User(DEFAULT_USER_USERNAME, DEFAULT_USER_PASSWORD));
  }

  @Test
  void shouldThrowExceptionWhenUserIdIsNull() {
    assertThrows(ValidatorException.class,
        () -> new User(null, DEFAULT_USER_USERNAME, DEFAULT_USER_PASSWORD));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"a"})
  void shouldThrowExceptionWhenUserNameIsInvalid(String name) {
    assertThrows(ValidatorException.class,
        () -> new User(name, DEFAULT_USER_PASSWORD));
  }

  @Test
  void shouldThrowExceptionWhenUserNameMaxLengthIsGreaterThan500Characters() {
    var name = String.join("", Collections.nCopies(501, "a"));
    assertThrows(ValidatorException.class,
        () -> new User(name, DEFAULT_USER_PASSWORD));
  }
}