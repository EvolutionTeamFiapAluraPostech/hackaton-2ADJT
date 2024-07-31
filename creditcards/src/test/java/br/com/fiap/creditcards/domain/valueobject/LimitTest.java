package br.com.fiap.creditcards.domain.valueobject;

import static br.com.fiap.creditcards.domain.valueobject.Limit.LIMIT_VALUE_CANNOT_BE_LESS_THAN_ZERO_MESSAGE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.creditcards.domain.exception.ValidatorException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LimitTest {

  @Test
  void shouldThrowValidatorExceptionWhenLimitValueIsLessThenZero() {
    var limit = "-1";
    assertThatThrownBy(() -> new Limit(new BigDecimal(limit)))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(LIMIT_VALUE_CANNOT_BE_LESS_THAN_ZERO_MESSAGE.formatted(limit));
  }

  @ParameterizedTest
  @ValueSource(strings = {"0", "1"})
  void shouldValidateLimitValue(String limit) {
    assertThatCode(() -> new Limit(new BigDecimal(limit)))
        .doesNotThrowAnyException();
  }
}
