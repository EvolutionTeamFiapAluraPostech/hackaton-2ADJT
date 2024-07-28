package br.com.fiap.customers.domain.valueobject;

import br.com.fiap.customers.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public record Email(String address) {

  public static final String EMAIL_FIELD_NAME = "email";
  public static final String EMAIL_INVALID = "Invalid email address. %s";
  public static final String EMAIL_REGEX = "^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

  public Email {
    if (address == null || !address.matches(EMAIL_REGEX)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), EMAIL_FIELD_NAME,
              EMAIL_INVALID.formatted(address)));
    }
  }
}
