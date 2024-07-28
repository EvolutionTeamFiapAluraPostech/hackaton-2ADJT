package br.com.fiap.customers.domain.valueobject;

import static br.com.fiap.customers.domain.valueobject.Street.STREET_FIELD_MAX_LENGTH;
import static br.com.fiap.customers.domain.valueobject.Street.STREET_MAX_LENGTH_IS_255_YOU_TYPED_MESSAGE;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_STREET;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.customers.domain.exception.ValidatorException;
import br.com.fiap.customers.shared.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class StreetTest {

  @Test
  void shouldThrowValidatorExceptionWhenStreetMaxLengthIsInvalid() {
    var streetLength = 256;
    var street = StringUtil.generateStringLength(streetLength);

    assertThatThrownBy(() -> new Street(street))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(STREET_MAX_LENGTH_IS_255_YOU_TYPED_MESSAGE.formatted(STREET_FIELD_MAX_LENGTH,
            streetLength));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldDoNothingWhenStreetIsNullOrEmpty(String street) {
    assertThatCode(() -> new Street(street))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldDoNothingWhenStreetMaxLengthIsValid() {
    assertThatCode(() -> new Street(DEFAULT_CUSTOMER_STREET))
        .doesNotThrowAnyException();
  }
}
