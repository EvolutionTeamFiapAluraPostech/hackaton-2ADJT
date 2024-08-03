package br.com.fiap.payments.domain.valueobject;

import static br.com.fiap.payments.domain.valueobject.Cvv.CVV_LENGTH;
import static br.com.fiap.payments.domain.valueobject.Cvv.CVV_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE;
import static br.com.fiap.payments.domain.valueobject.Cvv.CVV_VALUE_LENGTH_INVALID_MESSAGE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.payments.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CvvTest {

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowValidatorExceptionWhenCreditCardCvvIsNullOrEmpty(String cvv) {
    assertThatThrownBy(() -> new Cvv(cvv))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(CVV_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE.formatted(cvv));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "123456"})
  void shouldThrowValidatorExceptionWhenCreditCardCvvLengthIsInvalid(
      String cvv) {
    assertThatThrownBy(() -> new Cvv(cvv))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(CVV_VALUE_LENGTH_INVALID_MESSAGE.formatted(CVV_LENGTH, cvv));
  }

  @Test
  void shouldValidateCreditCardCvv() {
    var cvv = "123";
    assertThatCode(() -> new Cvv(cvv)).doesNotThrowAnyException();
  }
}