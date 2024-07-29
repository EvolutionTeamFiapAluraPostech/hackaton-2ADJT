package br.com.fiap.creditcards.presentation.api;

import br.com.fiap.creditcards.presentation.dto.CreditCardInputDto;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CreditCardsApi", description = "API de cadastro de cartão de crédito")
public interface CreditCardsApi {

  void postCreditCard(CreditCardInputDto creditCardInputDto);

}
