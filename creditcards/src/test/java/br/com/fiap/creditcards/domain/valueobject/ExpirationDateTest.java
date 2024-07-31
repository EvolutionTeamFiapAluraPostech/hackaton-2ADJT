package br.com.fiap.creditcards.domain.valueobject;

import static br.com.fiap.creditcards.domain.valueobject.ExpirationDate.EXPIRATION_DATE_LENGTH;
import static br.com.fiap.creditcards.domain.valueobject.ExpirationDate.EXPIRATION_DATE_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE;
import static br.com.fiap.creditcards.domain.valueobject.ExpirationDate.EXPIRATION_DATE_VALUE_LENGTH_INVALID_MESSAGE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.creditcards.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class ExpirationDateTest {

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowValidatorExceptionWhenCreditCardExpirationDateIsNullOrEmpty(
      String expirationDate) {
    assertThatThrownBy(() -> new ExpirationDate(expirationDate))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(
            EXPIRATION_DATE_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE.formatted(expirationDate));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "123456"})
  void shouldThrowValidatorExceptionWhenCreditCardExpirationDateLengthIsInvalid(
      String expirationDate) {
    assertThatThrownBy(() -> new ExpirationDate(expirationDate))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(
            EXPIRATION_DATE_VALUE_LENGTH_INVALID_MESSAGE.formatted(EXPIRATION_DATE_LENGTH,
                expirationDate));
  }

  @Test
  void shouldValidateCreditCardExpirationDate() {
    var expirationDate = "12/24";
    assertThatCode(() -> new ExpirationDate(expirationDate)).doesNotThrowAnyException();
  }
}
