package br.com.fiap.creditcards.presentation.api;

import br.com.fiap.creditcards.application.usecase.CreateCreditCardUseCase;
import br.com.fiap.creditcards.presentation.dto.CreditCardInputDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cartao")
public class CreditCardsController implements CreditCardsApi {

  private final CreateCreditCardUseCase createCreditCardUseCase;

  public CreditCardsController(CreateCreditCardUseCase createCreditCardUseCase) {
    this.createCreditCardUseCase = createCreditCardUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Override
  public void postCreditCard(@RequestBody CreditCardInputDto creditCardInputDto) {
    createCreditCardUseCase.execute(creditCardInputDto);
  }
}
