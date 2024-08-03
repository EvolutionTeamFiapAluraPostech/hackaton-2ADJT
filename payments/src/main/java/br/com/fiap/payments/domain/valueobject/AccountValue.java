package br.com.fiap.payments.domain.valueobject;

import br.com.fiap.payments.domain.exception.ValidatorException;
import java.math.BigDecimal;
import org.springframework.validation.FieldError;

public record AccountValue(BigDecimal paymentValue) {

  public static final String VALUE_FIELD = "valor";
  public static final String PAYMENT_VALUE_CANNOT_BE_LESS_THAN_ZERO_MESSAGE = "O valor do pagamento não pode ser menor ou igual a zero. Você informou %s.";
  public static final String PAYMENT_VALUE_CANNOT_BE_GREATER_THAN_LIMIT_VALUE_MESSAGE = "O valor do pagamento não pode ser maior do que o seu limite. Seu limite é %s e você informou %s.";


  public AccountValue {
    if (paymentValue.compareTo(BigDecimal.ZERO) <= 0) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), VALUE_FIELD,
          PAYMENT_VALUE_CANNOT_BE_LESS_THAN_ZERO_MESSAGE.formatted(paymentValue)));
    }
  }
}
