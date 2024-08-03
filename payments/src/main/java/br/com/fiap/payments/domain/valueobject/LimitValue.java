package br.com.fiap.payments.domain.valueobject;

import br.com.fiap.payments.domain.exception.ValidatorException;
import java.math.BigDecimal;
import org.springframework.validation.FieldError;

public record LimitValue(BigDecimal limitValue) {

  public static final String LIMIT_FIELD = "limite";
  public static final String LIMIT_VALUE_MUST_BE_GREATER_THAN_ZERO_MESSAGE = "O valor do limite deve ser maior do que zero. Você informou %s.";

  public LimitValue {
    if (limitValue.compareTo(BigDecimal.ZERO) <= 0) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), LIMIT_FIELD,
          LIMIT_VALUE_MUST_BE_GREATER_THAN_ZERO_MESSAGE.formatted(limitValue)));
    }
  }
}
