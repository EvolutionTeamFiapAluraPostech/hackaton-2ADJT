package br.com.fiap.customers.domain.valueobject;

import br.com.fiap.customers.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public record PhoneNumber(String phoneNumberValue) {

  public static final String PHONE_NUMBER_FIELD = "phoneNumber";
  public static final String PHONE_NUMBER_NOT_NULL_MESSAGE = "Phone number cannot be null.";
  public static final String PHONE_NUMBER_NOT_EMPTY_MESSAGE = "Phone number cannot be empty.";

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
