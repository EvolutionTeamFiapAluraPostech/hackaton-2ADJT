package br.com.fiap.creditcards.presentation.api;

import br.com.fiap.creditcards.application.usecase.CreateCreditCardUseCase;
import br.com.fiap.creditcards.application.usecase.GetCreditCardByNumberAndCustomerCpfUseCase;
import br.com.fiap.creditcards.presentation.dto.CreditCardInputDto;
import br.com.fiap.creditcards.presentation.dto.CreditCardOutputDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cartao")
public class CreditCardsController implements CreditCardsApi {

  private final CreateCreditCardUseCase createCreditCardUseCase;
  private final GetCreditCardByNumberAndCustomerCpfUseCase getCreditCardByNumberAndCustomerCpfUseCase;

  public CreditCardsController(CreateCreditCardUseCase createCreditCardUseCase,
      GetCreditCardByNumberAndCustomerCpfUseCase getCreditCardByNumberAndCustomerCpfUseCase) {
    this.createCreditCardUseCase = createCreditCardUseCase;
    this.getCreditCardByNumberAndCustomerCpfUseCase = getCreditCardByNumberAndCustomerCpfUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Override
  public void postCreditCard(@RequestBody CreditCardInputDto creditCardInputDto) {
    createCreditCardUseCase.execute(creditCardInputDto);
  }

  @GetMapping("/{numero}/cliente/{cpf}")
  @ResponseStatus(HttpStatus.OK)
  @Override
  public CreditCardOutputDto getCreditCardByNumberAndCustomerCpf(@PathVariable String numero,
      @PathVariable String cpf) {
    var creditCard = getCreditCardByNumberAndCustomerCpfUseCase.execute(numero, cpf);
    return CreditCardOutputDto.from(creditCard);
  }
}
