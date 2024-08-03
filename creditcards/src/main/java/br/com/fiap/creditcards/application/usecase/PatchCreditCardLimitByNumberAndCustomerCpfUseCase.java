package br.com.fiap.creditcards.application.usecase;

import br.com.fiap.creditcards.domain.entity.CreditCard;
import br.com.fiap.creditcards.presentation.dto.CreditCardPaymentValueDto;

public interface PatchCreditCardLimitByNumberAndCustomerCpfUseCase {

  CreditCard execute(String number, String cpf, CreditCardPaymentValueDto creditCardPaymentValueDto);

}
