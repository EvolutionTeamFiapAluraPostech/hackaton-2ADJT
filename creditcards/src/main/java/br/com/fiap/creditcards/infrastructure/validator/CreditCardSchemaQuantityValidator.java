package br.com.fiap.creditcards.infrastructure.validator;

import static br.com.fiap.creditcards.domain.valueobject.Cpf.CPF_FIELD;

import br.com.fiap.creditcards.application.gateway.CreditCardGateway;
import br.com.fiap.creditcards.application.validator.CreditCardQuantityValidator;
import br.com.fiap.creditcards.domain.exception.CreditCardMaxQuantityReachedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class CreditCardSchemaQuantityValidator implements CreditCardQuantityValidator {

  public static final Integer CREDIT_CARD_VALID_QUANTITY = 2;
  public static final String CREDIT_CARD_VALID_MESSAGE = "O número máximo permitido de cartões de crédito é %s!";
  private final CreditCardGateway creditCardGateway;

  public CreditCardSchemaQuantityValidator(CreditCardGateway creditCardGateway) {
    this.creditCardGateway = creditCardGateway;
  }

  @Override
  public void validate(String cpf) {
    var creditCards = creditCardGateway.findByCpf(cpf);
    if (!creditCards.isEmpty() && creditCards.size() == CREDIT_CARD_VALID_QUANTITY) {
      throw new CreditCardMaxQuantityReachedException(
          new FieldError(this.getClass().getSimpleName(), CPF_FIELD,
              CREDIT_CARD_VALID_MESSAGE.formatted(CREDIT_CARD_VALID_QUANTITY)));
    }
  }
}
