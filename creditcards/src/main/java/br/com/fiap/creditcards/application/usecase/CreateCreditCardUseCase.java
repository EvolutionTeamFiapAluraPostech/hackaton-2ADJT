package br.com.fiap.creditcards.application.usecase;

import br.com.fiap.creditcards.presentation.dto.CreditCardInputDto;

public interface CreateCreditCardUseCase {

  void execute(CreditCardInputDto creditCardInputDto);
}
