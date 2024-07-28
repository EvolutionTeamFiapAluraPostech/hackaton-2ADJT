package br.com.fiap.customers.domain.valueobject;

import br.com.fiap.customers.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class PostalCode {

  public static final Integer POSTAL_CODE_FIELD_MAX_LENGTH = 8;
  public static final String POSTAL_CODE_MAX_LENGTH_IS_8_YOU_TYPED_MESSAGE = "Postal code max length is %s. You typed %s";
  private static final String POSTAL_CODE_FIELD = "postalCode";
  private final String postalCodeValue;

  public PostalCode(String postalCodeValue) {
    validatePostalCodeMaxLength(postalCodeValue);
    this.postalCodeValue = postalCodeValue;
  }

  private void validatePostalCodeMaxLength(String postalCodeValue) {
    if (postalCodeValue != null && !postalCodeValue.trim().isEmpty()) {
      var postalCodeLength = postalCodeValue.trim().length();
      if (postalCodeLength > POSTAL_CODE_FIELD_MAX_LENGTH) {
        throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), POSTAL_CODE_FIELD,
            POSTAL_CODE_MAX_LENGTH_IS_8_YOU_TYPED_MESSAGE.formatted(POSTAL_CODE_FIELD_MAX_LENGTH,
                postalCodeLength)));
      }
    }
  }

  public String getPostalCodeValue() {
    return postalCodeValue;
  }
}
