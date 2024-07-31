package br.com.fiap.creditcards.domain.exception;

import lombok.Getter;
import org.springframework.validation.FieldError;

@Getter
public class CreditCardMaxQuantityReachedException extends RuntimeException {

  private final FieldError fieldError;

  public CreditCardMaxQuantityReachedException(FieldError fieldError) {
    super(fieldError.getDefaultMessage());
    this.fieldError = fieldError;
  }
}
