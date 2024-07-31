package br.com.fiap.creditcards.domain.valueobject;

import br.com.fiap.creditcards.domain.exception.ValidatorException;
import java.math.BigDecimal;
import org.springframework.validation.FieldError;

public record Limit(BigDecimal limit) {

  public static final String LIMIT_FIELD = "limite";
  public static final String LIMIT_VALUE_CANNOT_BE_LESS_THAN_ZERO_MESSAGE = "O valor do limite não pode ser menor do que zero. Você informou %s.";

  public Limit {
    if (limit.compareTo(BigDecimal.ZERO) < 0) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), LIMIT_FIELD,
          LIMIT_VALUE_CANNOT_BE_LESS_THAN_ZERO_MESSAGE.formatted(limit)));
    }
  }
}
