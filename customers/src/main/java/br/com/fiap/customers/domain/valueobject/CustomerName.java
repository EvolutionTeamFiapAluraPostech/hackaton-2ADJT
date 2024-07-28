package br.com.fiap.customers.domain.valueobject;

import br.com.fiap.customers.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class CustomerName {

  public static final String NAME_FIELD = "name";
  public static final String NAME_NOT_NULL_MESSAGE = "Customer name cannot be null";
  public static final String NAME_NOT_EMPTY_MESSAGE = "Customer name cannot be empty";
  public static final String NAME_MIN_LENGTH_INVALID_MESSAGE = "Customer name min length is invalid. Min length is 2";
  public static final String NAME_MAX_LENGTH_INVALID_MESSAGE = "Customer name max length is invalid. Max length is 500";
  private final String name;

  public CustomerName(String name) {
    this.validateNotNullOrNotEmptyName(name);
    this.validateMinLengthOf(name);
    this.validateMaxLengthOf(name);
    this.name = name;
  }

  private void validateMaxLengthOf(String name) {
    if (name.trim().length() > 500) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), NAME_FIELD,
          NAME_MAX_LENGTH_INVALID_MESSAGE));
    }
  }

  private void validateMinLengthOf(String name) {
    if (name.trim().length() < 2) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), NAME_FIELD,
          NAME_MIN_LENGTH_INVALID_MESSAGE));
    }
  }

  private void validateNotNullOrNotEmptyName(String name) {
    if (name == null) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), NAME_FIELD,
          NAME_NOT_NULL_MESSAGE));
    }
    if (name.trim().isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), NAME_FIELD,
          NAME_NOT_EMPTY_MESSAGE));
    }
  }

  public String getName() {
    return name;
  }
}
