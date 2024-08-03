package br.com.fiap.payments.infrastructure.validator;

import static br.com.fiap.payments.domain.valueobject.AccountValue.PAYMENT_VALUE_CANNOT_BE_GREATER_THAN_LIMIT_VALUE_MESSAGE;

import br.com.fiap.payments.application.validator.CreditCardLimitValidator;
import br.com.fiap.payments.domain.exception.ValidatorException;
import br.com.fiap.payments.domain.valueobject.AccountValue;
import br.com.fiap.payments.domain.valueobject.LimitValue;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class CreditCardInteractorLimitValidator implements CreditCardLimitValidator {

  @Override
  public void validate(String creditCardLimit, String paymentValue) {
    var limitValue = new LimitValue(new BigDecimal(creditCardLimit));
    var accountValue = new AccountValue(new BigDecimal(paymentValue));
    if (accountValue.paymentValue().compareTo(limitValue.limitValue()) > 0) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), AccountValue.VALUE_FIELD,
              PAYMENT_VALUE_CANNOT_BE_GREATER_THAN_LIMIT_VALUE_MESSAGE.formatted(
                  limitValue.limitValue(), accountValue.paymentValue())));
    }
  }
}
