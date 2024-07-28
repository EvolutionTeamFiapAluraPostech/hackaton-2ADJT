package br.com.fiap.customers.domain.valueobject;

import static br.com.fiap.customers.domain.valueobject.City.CITY_FIELD_MAX_LENGTH;
import static br.com.fiap.customers.domain.valueobject.City.CITY_MAX_LENGTH_IS_100_YOU_TYPED_MESSAGE;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CITY;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.customers.domain.exception.ValidatorException;
import br.com.fiap.customers.shared.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class CityTest {

  @Test
  void shouldThrowValidatorExceptionWhenCityMaxLengthIsInvalid() {
    var cityLength = 101;
    var city = StringUtil.generateStringLength(cityLength);

    assertThatThrownBy(() -> new City(city))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(CITY_MAX_LENGTH_IS_100_YOU_TYPED_MESSAGE.formatted(CITY_FIELD_MAX_LENGTH,
            cityLength));
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldDoNothingWhenCityIsNullOrEmpty(String city) {
    assertThatCode(() -> new City(city))
        .doesNotThrowAnyException();
  }

  @Test
  void shouldDoNothingWhenCityMaxLengthIsValid() {
    assertThatCode(() -> new City(DEFAULT_CUSTOMER_CITY))
        .doesNotThrowAnyException();
  }
}