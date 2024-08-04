package br.com.fiap.payments.domain.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;

class LimitInvalidExceptionTest {

  @Test
  void shouldConstructClass() {
    var limitInvalidException = new LimitInvalidException(
        new FieldError(this.getClass().getSimpleName(), "field", "defaultMessage"));

    assertThat(limitInvalidException).isNotNull();
    assertThat(limitInvalidException.getFieldError()).isNotNull();
  }
}