package br.com.fiap.customers.domain.valueobject;

import br.com.fiap.customers.domain.exception.ValidatorException;
import java.util.regex.Pattern;
import org.springframework.validation.FieldError;

public class PostalCode {

  public static final Integer POSTAL_CODE_FIELD_MAX_LENGTH = 8;
  public static final String POSTAL_CODE_MAX_LENGTH_IS_8_YOU_TYPED_MESSAGE = "O tamanho máximo para o CEP é %s. Você informou %s";
  public static final String POSTAL_CODE_ACCEPT_ONLY_NUMBERS = "Informe apenas números para o CEP. Você informou %s";
  private static final String POSTAL_CODE_VALID_FORMAT = "[0-9]*?[1-9][0-9]*";
  private static final String POSTAL_CODE_FIELD = "cep";
  private final String postalCodeValue;

  public PostalCode(String postalCodeValue) {
    validatePostalCodeMaxLength(postalCodeValue);
    validatePostalCodeOnlyNumbers(postalCodeValue);
    this.postalCodeValue = postalCodeValue;
  }

  private void validatePostalCodeOnlyNumbers(String postalCodeValue) {
    if (postalCodeValue != null && !postalCodeValue.trim().isEmpty()) {
      var validPostalCode = Pattern.compile(POSTAL_CODE_VALID_FORMAT).matcher(postalCodeValue)
          .matches();
      if (!validPostalCode) {
        throw new ValidatorException(
            new FieldError(this.getClass().getSimpleName(), POSTAL_CODE_FIELD,
                POSTAL_CODE_ACCEPT_ONLY_NUMBERS.formatted(postalCodeValue)));
      }
    }
  }

  private void validatePostalCodeMaxLength(String postalCodeValue) {
    if (postalCodeValue != null && !postalCodeValue.trim().isEmpty()) {
      var postalCodeLength = postalCodeValue.trim().length();
      if (postalCodeLength > POSTAL_CODE_FIELD_MAX_LENGTH) {
        throw new ValidatorException(
            new FieldError(this.getClass().getSimpleName(), POSTAL_CODE_FIELD,
                POSTAL_CODE_MAX_LENGTH_IS_8_YOU_TYPED_MESSAGE.formatted(
                    POSTAL_CODE_FIELD_MAX_LENGTH,
                    postalCodeLength)));
      }
    }
  }

  public String getPostalCodeValue() {
    return postalCodeValue;
  }
}
