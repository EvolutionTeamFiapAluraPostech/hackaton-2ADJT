package br.com.fiap.creditcards.infrastructure.usecase;

import br.com.fiap.creditcards.application.gateway.CreditCardGateway;
import br.com.fiap.creditcards.application.usecase.CreateCreditCardUseCase;
import br.com.fiap.creditcards.application.validator.CreditCardNumberAlreadyExistsValidator;
import br.com.fiap.creditcards.application.validator.CreditCardQuantityValidator;
import br.com.fiap.creditcards.domain.entity.CreditCard;
import br.com.fiap.creditcards.presentation.dto.CreditCardInputDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCreditCardInteractor implements CreateCreditCardUseCase {

  private final CreditCardGateway creditCardGateway;
  private final CreditCardNumberAlreadyExistsValidator creditCardNumberAlreadyExistsValidator;
  private final CreditCardQuantityValidator creditCardQuantityValidator;

  public CreateCreditCardInteractor(CreditCardGateway creditCardGateway,
      CreditCardNumberAlreadyExistsValidator creditCardNumberAlreadyExistsValidator,
      CreditCardQuantityValidator creditCardQuantityValidator) {
    this.creditCardGateway = creditCardGateway;
    this.creditCardNumberAlreadyExistsValidator = creditCardNumberAlreadyExistsValidator;
    this.creditCardQuantityValidator = creditCardQuantityValidator;
  }

  @Transactional
  @Override
  public void execute(CreditCardInputDto creditCardInputDto) {
    var creditCard = new CreditCard(creditCardInputDto.cpf(), creditCardInputDto.limite(),
        creditCardInputDto.numero(), creditCardInputDto.data_validade(),
        creditCardInputDto.cvv());
    creditCardNumberAlreadyExistsValidator.validate(creditCardInputDto.numero());
    creditCardQuantityValidator.validate(creditCardInputDto.cpf());
    creditCardGateway.save(creditCard);
  }
}
