package br.com.fiap.authentication.user.domain.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;

class DuplicatedExceptionTest {

  public static final String FIELD = "Field";
  public static final String MESSAGE = "Message";

  @Test
  void shouldConstructExceptionClass() {
    var validatorException = new DuplicatedException(new FieldError(this.getClass().getSimpleName(),
        FIELD, MESSAGE));

    assertThat(validatorException).isNotNull().isInstanceOf(DuplicatedException.class);
    assertThat(validatorException.getFieldError()).isNotNull();
    assertThat(validatorException.getFieldError().getField()).isNotNull().isEqualTo(FIELD);
    assertThat(validatorException.getFieldError().getDefaultMessage()).isNotNull()
        .isEqualTo(MESSAGE);
  }
}
