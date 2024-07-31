package br.com.fiap.creditcards.domain.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;

class NoResultExceptionTest {

  @Test
  void shouldConstructClass() {
    var noResultException = new NoResultException(
        new FieldError(this.getClass().getSimpleName(), "field", "defaultMessage"));

    assertThat(noResultException).isNotNull();
    assertThat(noResultException.getFieldError()).isNotNull();
  }
}