package br.com.fiap.authentication.user.domain.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;

class NoResultExceptionTest {

  public static final String FIELD = "Field";
  public static final String MESSAGE = "Message";

  @Test
  void shouldConstructExceptionClass() {
    var validatorException = new NoResultException(new FieldError(this.getClass().getSimpleName(),
        FIELD, MESSAGE));

    assertThat(validatorException).isNotNull().isInstanceOf(NoResultException.class);
    assertThat(validatorException.getFieldError()).isNotNull();
    assertThat(validatorException.getFieldError().getField()).isNotNull().isEqualTo(FIELD);
    assertThat(validatorException.getFieldError().getDefaultMessage()).isNotNull()
        .isEqualTo(MESSAGE);
  }
}