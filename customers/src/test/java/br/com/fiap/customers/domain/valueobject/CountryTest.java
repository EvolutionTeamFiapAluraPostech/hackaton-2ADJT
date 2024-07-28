package br.com.fiap.customers.domain.valueobject;

import static br.com.fiap.customers.domain.valueobject.Country.COUNTRY_FIELD_MAX_LENGTH;
import static br.com.fiap.customers.domain.valueobject.Country.COUNTRY_MAX_LENGTH_IS_100_YOU_TYPED_MESSAGE;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_COUNTRY;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.customers.domain.exception.ValidatorException;
import br.com.fiap.customers.shared.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CountryTest {

  @Test
  void shouldThrowValidatorExceptionWhenCountryMaxLengthIsInvalid() {
    var countryLength = 101;
    var country = StringUtil.generateStringLength(countryLength);

    assertThatThrownBy(() -> new Country(country))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(COUNTRY_MAX_LENGTH_IS_100_YOU_TYPED_MESSAGE.formatted(COUNTRY_FIELD_MAX_LENGTH,
            countryLength));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldDoNothingWhenCountryIsNullOrEmpty(String country) {
    assertThatCode(() -> new Country(country))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldDoNothingWhenCountryMaxLengthIsValid() {
    assertThatCode(() -> new Country(DEFAULT_CUSTOMER_COUNTRY))
        .doesNotThrowAnyException();
  }
}