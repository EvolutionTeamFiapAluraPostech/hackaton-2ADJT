package br.com.fiap.payments.domain.valueobject;

import static br.com.fiap.payments.domain.valueobject.LimitValue.LIMIT_VALUE_MUST_BE_GREATER_THAN_ZERO_MESSAGE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.payments.domain.exception.ValidatorException;
import java.math.BigDecimal;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LimitValueTest {

  @ParameterizedTest
  @ValueSource(strings = {"0", "-1"})
  void shouldThrowValidatorExceptionWhenLimitValueIsLessThenZero(String limit) {
    assertThatThrownBy(() -> new LimitValue(new BigDecimal(limit)))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(LIMIT_VALUE_MUST_BE_GREATER_THAN_ZERO_MESSAGE.formatted(limit));
  }

  @ParameterizedTest
  @ValueSource(strings = {"0.01", "1"})
  void shouldValidateLimitValue(String limit) {
    assertThatCode(() -> new LimitValue(new BigDecimal(limit)))
        .doesNotThrowAnyException();
  }
}