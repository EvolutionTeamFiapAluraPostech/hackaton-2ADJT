package br.com.fiap.customers.domain.valueobject;

import br.com.fiap.customers.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class City {

  public static final Integer CITY_FIELD_MAX_LENGTH = 100;
  public static final String CITY_MAX_LENGTH_IS_100_YOU_TYPED_MESSAGE = "O tamanho máximo para a cidade é %s. Você informou %s";
  private static final String CITY_FIELD = "cidade";
  private final String cityValue;

  public City(String cityValue) {
    validateCityMaxLength(cityValue);
    this.cityValue = cityValue;
  }

  private void validateCityMaxLength(String cityValue) {
    if (cityValue != null && !cityValue.trim().isEmpty()) {
      var cityLength = cityValue.trim().length();
      if (cityLength > CITY_FIELD_MAX_LENGTH) {
        throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), CITY_FIELD,
            CITY_MAX_LENGTH_IS_100_YOU_TYPED_MESSAGE.formatted(CITY_FIELD_MAX_LENGTH, cityLength)));
      }
    }
  }

  public String getCityValue() {
    return cityValue;
  }
}
