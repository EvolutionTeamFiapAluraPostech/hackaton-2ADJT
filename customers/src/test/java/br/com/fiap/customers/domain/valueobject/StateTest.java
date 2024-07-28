package br.com.fiap.customers.domain.valueobject;

import static br.com.fiap.customers.domain.valueobject.State.STATE_FIELD_MAX_LENGTH;
import static br.com.fiap.customers.domain.valueobject.State.STATE_MAX_LENGTH_IS_100_YOU_TYPED_MESSAGE;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_STATE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.customers.domain.exception.ValidatorException;
import br.com.fiap.customers.shared.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class StateTest {

  @Test
  void shouldThrowValidatorExceptionWhenStateMaxLengthIsInvalid() {
    var stateLength = 101;
    var state = StringUtil.generateStringLength(stateLength);

    assertThatThrownBy(() -> new State(state))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(STATE_MAX_LENGTH_IS_100_YOU_TYPED_MESSAGE.formatted(STATE_FIELD_MAX_LENGTH,
            stateLength));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldDoNothingWhenStateIsNullOrEmpty(String state) {
    assertThatCode(() -> new State(state))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldDoNothingWhenStateMaxLengthIsValid() {
    assertThatCode(() -> new State(DEFAULT_CUSTOMER_STATE))
        .doesNotThrowAnyException();
  }
}
