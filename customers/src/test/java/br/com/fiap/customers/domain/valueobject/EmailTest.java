package br.com.fiap.customers.domain.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import br.com.fiap.customers.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class EmailTest {

  private static final String DEFAULT_USER_EMAIL = "email@domain.com";

  @Test
  void shouldCreateEmail() {
    assertDoesNotThrow(() -> new Email(DEFAULT_USER_EMAIL));
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"email.domain.com", " email.domain.com", "@", "1", "email@domain",
      "A@b@c@example.com", "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com", "email @example.com"})
  void shouldThrowExceptionWhenEmailIsInvalid(String email) {
    assertThrows(ValidatorException.class, () -> new Email(email));
  }
}
