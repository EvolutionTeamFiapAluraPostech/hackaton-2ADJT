package br.com.fiap.payments.domain.valueobject;

import static br.com.fiap.payments.domain.valueobject.AccountValue.PAYMENT_VALUE_CANNOT_BE_LESS_THAN_ZERO_MESSAGE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.payments.domain.exception.ValidatorException;
import java.math.BigDecimal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AccountValueTest {

  @ParameterizedTest
  @ValueSource(strings = {"-1", "0"})
  void shouldThrowValidatorExceptionWhenLimitValueIsLessThenZero(String value) {
    assertThatThrownBy(() -> new AccountValue(new BigDecimal(value)))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(PAYMENT_VALUE_CANNOT_BE_LESS_THAN_ZERO_MESSAGE.formatted(value));
  }

  @ParameterizedTest
  @ValueSource(strings = {"0.01", "1"})
  void shouldValidateLimitValue(String value) {
    assertThatCode(() -> new AccountValue(new BigDecimal(value)))
        .doesNotThrowAnyException();
  }
}