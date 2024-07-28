package br.com.fiap.customers.domain.valueobject;

import br.com.fiap.customers.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class Street {

  public static final Integer STREET_FIELD_MAX_LENGTH = 255;
  public static final String STREET_MAX_LENGTH_IS_255_YOU_TYPED_MESSAGE = "Street max length is %s. You typed %s";
  private static final String STREET_FIELD = "street";
  private final String streetValue;

  public Street(String streetValue) {
    validateStreetMaxLength(streetValue);
    this.streetValue = streetValue;
  }

  private void validateStreetMaxLength(String streetValue) {
    if (streetValue != null && !streetValue.trim().isEmpty()) {
      var streetLength = streetValue.trim().length();
      if (streetLength > STREET_FIELD_MAX_LENGTH) {
        throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), STREET_FIELD,
            STREET_MAX_LENGTH_IS_255_YOU_TYPED_MESSAGE.formatted(STREET_FIELD_MAX_LENGTH,
                streetLength)));
      }
    }
  }

  public String getStreetValue() {
    return streetValue;
  }
}
