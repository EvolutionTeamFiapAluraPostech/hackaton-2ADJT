package br.com.fiap.authentication.user.presentation.exception;

import br.com.fiap.authentication.user.presentation.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<?> handlerUnauthorized(AuthenticationException exception) {
    var error = new FieldError(AuthenticationException.class.getSimpleName(), "Authentication",
        exception.getMessage());
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorDto(error));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handlerInternalServerError(Exception ex) {
    var error = ex.getMessage();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
