package br.com.fiap.payments.domain.exception;

import org.springframework.validation.FieldError;

public class LimitInvalidException extends RuntimeException {

  private final FieldError fieldError;

  public LimitInvalidException(FieldError fieldError) {
    super(fieldError.getDefaultMessage());
    this.fieldError = fieldError;
  }

  public FieldError getFieldError() {
    return fieldError;
  }
}
