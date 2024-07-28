package br.com.fiap.customers.domain.valueobject;

import static br.com.fiap.customers.domain.valueobject.PostalCode.POSTAL_CODE_FIELD_MAX_LENGTH;
import static br.com.fiap.customers.domain.valueobject.PostalCode.POSTAL_CODE_MAX_LENGTH_IS_8_YOU_TYPED_MESSAGE;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_POSTAL_CODE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.customers.domain.exception.ValidatorException;
import br.com.fiap.customers.shared.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class PostalCodeTest {

  @Test
  void shouldThrowValidatorExceptionWhenPostalCodeMaxLengthIsInvalid() {
    var postalCodeLength = 9;
    var postalCode = StringUtil.generateStringLength(postalCodeLength);

    assertThatThrownBy(() -> new PostalCode(postalCode))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(POSTAL_CODE_MAX_LENGTH_IS_8_YOU_TYPED_MESSAGE.formatted(POSTAL_CODE_FIELD_MAX_LENGTH,
            postalCodeLength));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldDoNothingWhenPostalCodeIsNullOrEmpty(String postalCode) {
    assertThatCode(() -> new PostalCode(postalCode))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldDoNothingWhenPostalCodeMaxLengthIsValid() {
    assertThatCode(() -> new PostalCode(DEFAULT_CUSTOMER_POSTAL_CODE))
        .doesNotThrowAnyException();
  }
}