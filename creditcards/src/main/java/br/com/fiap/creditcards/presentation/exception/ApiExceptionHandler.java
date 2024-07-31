package br.com.fiap.creditcards.presentation.exception;

import br.com.fiap.creditcards.domain.exception.CreditCardMaxQuantityReachedException;
import br.com.fiap.creditcards.domain.exception.DuplicatedException;
import br.com.fiap.creditcards.domain.exception.NoResultException;
import br.com.fiap.creditcards.domain.exception.ValidatorException;
import br.com.fiap.creditcards.presentation.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(ValidatorException.class)
  public ResponseEntity<?> handlerValidatorException(ValidatorException exception) {
    var error = exception.getFieldError();
    return ResponseEntity.badRequest().body(new ErrorDto(error));
  }

  @ExceptionHandler(DuplicatedException.class)
  public ResponseEntity<?> handlerDuplicatedException(DuplicatedException exception) {
    var error = exception.getFieldError();
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorDto(error));
  }

  @ExceptionHandler(NoResultException.class)
  public ResponseEntity<?> handlerNoResultException(NoResultException exception) {
    var error = exception.getFieldError();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(error));
  }

  @ExceptionHandler(CreditCardMaxQuantityReachedException.class)
  public ResponseEntity<?> handlerCreditCardMaxQuantityReachedException(
      CreditCardMaxQuantityReachedException exception) {
    var error = exception.getFieldError();
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(error));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handlerInternalServerError(Exception ex) {
    var error = ex.getMessage();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
