package br.com.fiap.customers.domain.valueobject;

import br.com.fiap.customers.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class Country {

  public static final Integer COUNTRY_FIELD_MAX_LENGTH = 100;
  public static final String COUNTRY_MAX_LENGTH_IS_100_YOU_TYPED_MESSAGE = "Country max length is %s. You typed %s";
  private static final String COUNTRY_FIELD = "country";
  private final String countryValue;

  public Country(String countryValue) {
    validateCountryMaxLength(countryValue);
    this.countryValue = countryValue;
  }

  private void validateCountryMaxLength(String countryValue) {
    if (countryValue != null && !countryValue.trim().isEmpty()) {
      var countryLength = countryValue.trim().length();
      if (countryLength > COUNTRY_FIELD_MAX_LENGTH) {
        throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), COUNTRY_FIELD,
            COUNTRY_MAX_LENGTH_IS_100_YOU_TYPED_MESSAGE.formatted(COUNTRY_FIELD_MAX_LENGTH,
                countryLength)));
      }
    }
  }

  public String getCountryValue() {
    return countryValue;
  }
}
