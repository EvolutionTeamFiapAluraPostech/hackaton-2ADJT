package br.com.fiap.creditcards.application.usecase;

import br.com.fiap.creditcards.domain.entity.CreditCard;

public interface GetCreditCardByNumberAndCustomerCpfUseCase {

  CreditCard execute(String creditCardNumber, String cpf);
}
