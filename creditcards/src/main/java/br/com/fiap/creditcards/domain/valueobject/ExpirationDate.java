package br.com.fiap.creditcards.domain.valueobject;

import br.com.fiap.creditcards.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class ExpirationDate {

  public static final Integer EXPIRATION_DATE_LENGTH = 5;
  public static final String EXPIRATION_DATE_FIELD = "data_validade";
  public static final String EXPIRATION_DATE_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE = "A data de validade do cartão de crédito não pode ser %s.";
  public static final String EXPIRATION_DATE_VALUE_LENGTH_INVALID_MESSAGE = "A data de validade do cartão de crédito deve possuir %s caracteres. Você informou %s.";
  private final String expirationDateValue;

  public ExpirationDate(String expirationDateValue) {
    validateNullOrEmpty(expirationDateValue);
    validateLength(expirationDateValue);
    this.expirationDateValue = expirationDateValue;
  }

  private void validateLength(String expirationDate) {
    if (expirationDate.length() != EXPIRATION_DATE_LENGTH) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), EXPIRATION_DATE_FIELD,
          EXPIRATION_DATE_VALUE_LENGTH_INVALID_MESSAGE.formatted(EXPIRATION_DATE_LENGTH, expirationDate)));
    }
  }

  private void validateNullOrEmpty(String expirationDate) {
    if (expirationDate == null || expirationDate.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), EXPIRATION_DATE_FIELD,
          EXPIRATION_DATE_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE.formatted(expirationDate)));
    }
  }

  public String getExpirationDateValue() {
    return expirationDateValue;
  }
}
