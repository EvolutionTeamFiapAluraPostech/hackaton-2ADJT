package br.com.fiap.payments.infrastructure.validator;

import static br.com.fiap.payments.domain.valueobject.Cvv.CVV_FIELD;
import static br.com.fiap.payments.domain.valueobject.Cvv.CVV_VALUE_INVALID_MESSAGE;

import br.com.fiap.payments.application.validator.CreditCardCvvValidator;
import br.com.fiap.payments.domain.exception.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class CreditCardInteractorCvvValidator implements CreditCardCvvValidator {

  @Override
  public void validate(String creditCardCvv, String paymentCreditCardCvv) {
    if (creditCardCvv != null && !creditCardCvv.equals(paymentCreditCardCvv)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), CVV_FIELD,
              CVV_VALUE_INVALID_MESSAGE.formatted(paymentCreditCardCvv)));
    }
  }
}
