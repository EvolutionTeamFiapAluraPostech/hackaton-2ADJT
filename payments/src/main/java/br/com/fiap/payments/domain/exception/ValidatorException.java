package br.com.fiap.payments.domain.exception;

import org.springframework.validation.FieldError;

public class ValidatorException extends RuntimeException {

  private final FieldError fieldError;

  public ValidatorException(FieldError fieldError) {
    super(fieldError.getDefaultMessage());
    this.fieldError = fieldError;
  }

  public FieldError getFieldError() {
    return fieldError;
  }
}
