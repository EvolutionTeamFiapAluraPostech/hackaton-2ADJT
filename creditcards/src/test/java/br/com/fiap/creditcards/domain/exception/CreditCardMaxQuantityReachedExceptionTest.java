package br.com.fiap.creditcards.domain.exception;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.validation.FieldError;

class CreditCardMaxQuantityReachedExceptionTest {

  @Test
  void shouldCreateValidatorExceptionClass() {
    var creditCardMaxQuantityReachedException = new CreditCardMaxQuantityReachedException(
        new FieldError(this.getClass().getSimpleName(), "field", "defaultMessage"));

    assertThat(creditCardMaxQuantityReachedException).isNotNull();
    assertThat(creditCardMaxQuantityReachedException.getFieldError()).isNotNull();
  }
}