package br.com.fiap.creditcards.infrastructure.usecase;

import br.com.fiap.creditcards.application.gateway.CreditCardGateway;
import br.com.fiap.creditcards.application.usecase.GetCreditCardByNumberAndCustomerCpfUseCase;
import br.com.fiap.creditcards.domain.entity.CreditCard;
import org.springframework.stereotype.Service;

@Service
public class GetCreditCardByNumberAndCustomerCpfInteractor implements
    GetCreditCardByNumberAndCustomerCpfUseCase {

  private final CreditCardGateway creditCardGateway;

  public GetCreditCardByNumberAndCustomerCpfInteractor(CreditCardGateway creditCardGateway) {
    this.creditCardGateway = creditCardGateway;
  }

  @Override
  public CreditCard execute(String creditCardNumber, String cpf) {
    return creditCardGateway.findByNumberAndCpfRequired(creditCardNumber, cpf);
  }
}
