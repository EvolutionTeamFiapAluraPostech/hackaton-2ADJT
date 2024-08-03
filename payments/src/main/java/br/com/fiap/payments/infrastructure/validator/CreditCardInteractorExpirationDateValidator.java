package br.com.fiap.payments.infrastructure.validator;

import static br.com.fiap.payments.domain.valueobject.ExpirationDate.EXPIRATION_DATE_FIELD;
import static br.com.fiap.payments.domain.valueobject.ExpirationDate.EXPIRATION_DATE_VALUE_INVALID_MESSAGE;

import br.com.fiap.payments.application.validator.CreditCardExpirationDateValidator;
import br.com.fiap.payments.domain.exception.ValidatorException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class CreditCardInteractorExpirationDateValidator implements
    CreditCardExpirationDateValidator {

  @Override
  public void validate(String creditCardExpirationDate, String paymentCreditCardExpirationDate) {
    if (creditCardExpirationDate != null && !creditCardExpirationDate.equals(
        paymentCreditCardExpirationDate)) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), EXPIRATION_DATE_FIELD,
              EXPIRATION_DATE_VALUE_INVALID_MESSAGE.formatted(paymentCreditCardExpirationDate)));
    }
  }
}
