package br.com.fiap.creditcards.domain.valueobject;

import br.com.fiap.creditcards.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class Cvv {

  public static final Integer CVV_LENGTH = 3;
  public static final String CVV_FIELD = "cvv";
  public static final String CVV_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE = "O código de segurança do cartão de crédito não pode ser %s.";
  public static final String CVV_VALUE_LENGTH_INVALID_MESSAGE = "O código de segurança do cartão de crédito deve possuir %s caracteres. Você informou %s.";
  private final String cvvValue;

  public Cvv(String cvvValue) {
    validateNullOrEmpty(cvvValue);
    validateLength(cvvValue);
    this.cvvValue = cvvValue;
  }

  private void validateLength(String expirationDate) {
    if (expirationDate.length() != CVV_LENGTH) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), CVV_FIELD,
          CVV_VALUE_LENGTH_INVALID_MESSAGE.formatted(CVV_LENGTH, expirationDate)));
    }
  }

  private void validateNullOrEmpty(String expirationDate) {
    if (expirationDate == null || expirationDate.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), CVV_FIELD,
          CVV_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE.formatted(expirationDate)));
    }
  }

  public String getCvvValue() {
    return cvvValue;
  }
}
