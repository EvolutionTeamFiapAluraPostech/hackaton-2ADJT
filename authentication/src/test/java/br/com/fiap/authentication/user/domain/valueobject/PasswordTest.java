package br.com.fiap.authentication.user.domain.valueobject;

import static br.com.fiap.authentication.shared.testData.user.UserTestData.DEFAULT_USER_PASSWORD;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiap.authentication.user.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PasswordTest {

  @Test
  void shouldAcceptPassword() {
    assertDoesNotThrow(() -> new Password(DEFAULT_USER_PASSWORD));
  }

  @ParameterizedTest
  @ValueSource(strings = {"a", "1", "B", "@", "abcdefghijk", "0ABCDEFGHI", "abcd1234", "Abcd1234",
      "@#$%^&+=B1"})
  void shouldThrowExceptionWhenCpfIsInvalid(String passwordValue) {
    assertThrows(ValidatorException.class, () -> new Password(passwordValue));
  }
}