package br.com.fiap.customers.domain.valueobject;

import br.com.fiap.customers.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public record PhoneNumber(String phoneNumberValue) {

  public static final String PHONE_NUMBER_FIELD = "telefone";
  public static final String PHONE_NUMBER_NOT_NULL_MESSAGE = "O número de telefone não pode ser nulo.";
  public static final String PHONE_NUMBER_NOT_EMPTY_MESSAGE = "O número de telefone não pode ser vazio.";

  public PhoneNumber {
    if (phoneNumberValue == null) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          PHONE_NUMBER_FIELD, PHONE_NUMBER_NOT_NULL_MESSAGE));
    }
    if (phoneNumberValue.trim().isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          PHONE_NUMBER_FIELD, PHONE_NUMBER_NOT_EMPTY_MESSAGE));
    }
  }
}
