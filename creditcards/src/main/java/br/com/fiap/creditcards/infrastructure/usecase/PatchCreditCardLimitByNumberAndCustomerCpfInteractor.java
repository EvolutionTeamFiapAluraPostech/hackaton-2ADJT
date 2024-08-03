package br.com.fiap.creditcards.infrastructure.usecase;

import br.com.fiap.creditcards.application.gateway.CreditCardGateway;
import br.com.fiap.creditcards.application.usecase.PatchCreditCardLimitByNumberAndCustomerCpfUseCase;
import br.com.fiap.creditcards.domain.entity.CreditCard;
import br.com.fiap.creditcards.presentation.dto.CreditCardPaymentValueDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatchCreditCardLimitByNumberAndCustomerCpfInteractor implements
    PatchCreditCardLimitByNumberAndCustomerCpfUseCase {

  private final CreditCardGateway creditCardGateway;

  public PatchCreditCardLimitByNumberAndCustomerCpfInteractor(CreditCardGateway creditCardGateway) {
    this.creditCardGateway = creditCardGateway;
  }

  @Transactional
  @Override
  public CreditCard execute(String number, String cpf, CreditCardPaymentValueDto creditCardPaymentValueDto) {
    var creditCard = creditCardGateway.findByNumberAndCpfRequired(number, cpf);
    var newLimit = creditCard.getNewLimit(creditCardPaymentValueDto.paymentValue());
    return creditCardGateway.updateLimit(creditCard, newLimit);
  }
}
