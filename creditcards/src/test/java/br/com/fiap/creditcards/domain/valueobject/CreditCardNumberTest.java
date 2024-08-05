package br.com.fiap.creditcards.domain.valueobject;

import static br.com.fiap.creditcards.domain.valueobject.CreditCardNumber.NUMBER_MAX_LENGTH;
import static br.com.fiap.creditcards.domain.valueobject.CreditCardNumber.NUMBER_VALUE_ACCEPTS_ONLY_NUMBER_MESSAGE;
import static br.com.fiap.creditcards.domain.valueobject.CreditCardNumber.NUMBER_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE;
import static br.com.fiap.creditcards.domain.valueobject.CreditCardNumber.NUMBER_VALUE_LENGTH_INVALID_MESSAGE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.creditcards.domain.exception.ValidatorException;
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
  @ValueSource(strings = {"ABCD", "123456789012AbCD", "1234A5678B9012C3456", "123D 567C 901B 345A",
      "ABCD EFGH IJKLM NOPQ", "1234 5678 9014 345"})
  void shouldThrowValidatorExceptionWhenCreditCardNumberHasAlfaCharacters(String number) {
    assertThatThrownBy(() -> new CreditCardNumber(number))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(NUMBER_VALUE_ACCEPTS_ONLY_NUMBER_MESSAGE.formatted(number));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "12345678901234567"})
  void shouldThrowValidatorExceptionWhenCreditCardNumberLengthIsInvalid(String number) {
    assertThatThrownBy(() -> new CreditCardNumber(number))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(NUMBER_VALUE_LENGTH_INVALID_MESSAGE.formatted(NUMBER_MAX_LENGTH, number));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1234567890123456", "1234 5678 9012 3456"})
  void shouldValidateCreditCardNumber(String number) {
    assertThatCode(() -> new CreditCardNumber(number)).doesNotThrowAnyException();
  }
}
