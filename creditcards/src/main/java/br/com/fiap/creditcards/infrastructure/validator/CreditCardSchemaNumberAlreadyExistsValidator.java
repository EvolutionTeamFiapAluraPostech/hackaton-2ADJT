package br.com.fiap.creditcards.infrastructure.validator;

import static br.com.fiap.creditcards.domain.valueobject.CreditCardNumber.NUMBER_FIELD;

import br.com.fiap.creditcards.application.gateway.CreditCardGateway;
import br.com.fiap.creditcards.application.validator.CreditCardNumberAlreadyExistsValidator;
import br.com.fiap.creditcards.domain.exception.DuplicatedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class CreditCardSchemaNumberAlreadyExistsValidator implements
    CreditCardNumberAlreadyExistsValidator {

  public static final String CREDIT_CARD_ALREADY_EXISTS = "Este número de cartão de crédito já está sendo utilizado! %s.";
  private final CreditCardGateway creditCardGateway;

  public CreditCardSchemaNumberAlreadyExistsValidator(CreditCardGateway creditCardGateway) {
    this.creditCardGateway = creditCardGateway;
  }

  @Override
  public void validate(String number) {
    var creditCard = creditCardGateway.findByNumber(number);
    if (creditCard != null) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(), NUMBER_FIELD,
          CREDIT_CARD_ALREADY_EXISTS.formatted(number)));
    }
  }
}
