package br.com.fiap.creditcards.infrastructure.exception;

import br.com.fiap.creditcards.domain.exception.NoResultException;
import br.com.fiap.creditcards.domain.exception.ValidatorException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.validation.FieldError;

public class CustomErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    return switch (response.status()) {
      case 400 -> new ValidatorException(new FieldError(this.getClass().getSimpleName(), "", ""));
      case 404 -> new NoResultException(new FieldError(this.getClass().getSimpleName(), "", ""));
      default -> new Exception("Generic error");
    };
  }
}
