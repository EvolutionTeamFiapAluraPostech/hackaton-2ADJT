package br.com.fiap.payments.presentation.exception;

import br.com.fiap.payments.domain.exception.LimitInvalidException;
import br.com.fiap.payments.domain.exception.NoResultException;
import br.com.fiap.payments.domain.exception.ValidatorException;
import br.com.fiap.payments.presentation.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(ValidatorException.class)
  public ResponseEntity<?> handlerValidatorException(ValidatorException exception) {
    var error = exception.getFieldError();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDto(error));
  }

  @ExceptionHandler(NoResultException.class)
  public ResponseEntity<?> handlerNoResultException(NoResultException exception) {
    var error = exception.getFieldError();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(error));
  }

  @ExceptionHandler(LimitInvalidException.class)
  public ResponseEntity<?> handlerLimitInvalidException(LimitInvalidException exception) {
    var error = exception.getFieldError();
    return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body(new ErrorDto(error));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handlerInternalServerError(Exception ex) {
    var error = ex.getMessage();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
