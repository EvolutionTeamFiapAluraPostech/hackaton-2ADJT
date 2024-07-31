package br.com.fiap.customers.domain.valueobject;

import br.com.fiap.customers.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class Email {

  public static final String EMAIL_FIELD_NAME = "email";
  public static final String EMAIL_NULL_OR_EMPTY_MESSAGE = "O endereço de email não pode ser nulo ou vazio. %s";
  public static final Integer EMAIL_MIN_LENGTH = 5;
  public static final String EMAIL_MIN_LENGTH_MESSAGE = "O endereço de email deve possuir no mínimo %s caracteres. %s";
  public static final Integer EMAIL_MAX_LENGTH = 500;
  public static final String EMAIL_MAX_LENGTH_MESSAGE = "O endereço de email deve possuir no máximo %s caracteres. %s";
  public static final String EMAIL_INVALID = "Endereço de email inválido. %s";
  public static final String EMAIL_REGEX = "^[a-zA-Z0-9._]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
  private final String address;

  public Email(String address) {
    validateNullOrEmpty(address);
    validateMinLength(address);
    validateMaxLength(address);
    validate(address);
    this.address = address;
  }

  private void validateMaxLength(String address) {
    var addressLength = address.trim().length();
    if (addressLength > EMAIL_MAX_LENGTH) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), EMAIL_FIELD_NAME,
              EMAIL_MAX_LENGTH_MESSAGE.formatted(EMAIL_MAX_LENGTH, addressLength)));
    }
  }

  private void validateMinLength(String address) {
    var addressLength = address.trim().length();
    if (addressLength < EMAIL_MIN_LENGTH) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), EMAIL_FIELD_NAME,
              EMAIL_MIN_LENGTH_MESSAGE.formatted(EMAIL_MIN_LENGTH, addressLength)));
    }
  }

  private void validateNullOrEmpty(String address) {
    if (address == null || address.isEmpty()) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), EMAIL_FIELD_NAME,
              EMAIL_NULL_OR_EMPTY_MESSAGE.formatted(address)));
    }
  }

  private void validate(String address) {
    if (address == null || !address.matches(EMAIL_REGEX)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), EMAIL_FIELD_NAME,
              EMAIL_INVALID.formatted(address)));
    }
  }

  public String getAddress() {
    return address;
  }
}
