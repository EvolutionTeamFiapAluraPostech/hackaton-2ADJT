package br.com.fiap.creditcards.domain.valueobject;

import static br.com.fiap.creditcards.domain.valueobject.CreditCardNumber.NUMBER_LENGTH;
import static br.com.fiap.creditcards.domain.valueobject.CreditCardNumber.NUMBER_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE;
import static br.com.fiap.creditcards.domain.valueobject.CreditCardNumber.NUMBER_VALUE_LENGTH_INVALID_MESSAGE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.creditcards.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CreditCardNumberTest {

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowValidatorExceptionWhenCreditCardNumberIsNullOrEmpty(String number) {
    assertThatThrownBy(() -> new CreditCardNumber(number))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(NUMBER_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE.formatted(number));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "12345678901234567"})
  void shouldThrowValidatorExceptionWhenCreditCardNumberLengthIsInvalid(String number) {
    assertThatThrownBy(() -> new CreditCardNumber(number))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(NUMBER_VALUE_LENGTH_INVALID_MESSAGE.formatted(NUMBER_LENGTH, number));
  }

  @Test
  void shouldValidateCreditCardNumber() {
    var number = "1234567890123456";
    assertThatCode(() -> new CreditCardNumber(number)).doesNotThrowAnyException();
  }
}
