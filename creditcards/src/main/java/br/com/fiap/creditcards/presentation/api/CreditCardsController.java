package br.com.fiap.creditcards.presentation.api;

import br.com.fiap.creditcards.application.usecase.CreateCreditCardUseCase;
import br.com.fiap.creditcards.application.usecase.GetCreditCardByNumberAndCustomerCpfUseCase;
import br.com.fiap.creditcards.application.usecase.PatchCreditCardLimitByNumberAndCustomerCpfUseCase;
import br.com.fiap.creditcards.presentation.dto.CreditCardInputDto;
import br.com.fiap.creditcards.presentation.dto.CreditCardOutputDto;
import br.com.fiap.creditcards.presentation.dto.CreditCardPaymentValueDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
  private final PatchCreditCardLimitByNumberAndCustomerCpfUseCase patchCreditCardLimitByNumberAndCustomerCpfUseCase;

  public CreditCardsController(CreateCreditCardUseCase createCreditCardUseCase,
      GetCreditCardByNumberAndCustomerCpfUseCase getCreditCardByNumberAndCustomerCpfUseCase,
      PatchCreditCardLimitByNumberAndCustomerCpfUseCase patchCreditCardLimitByNumberAndCustomerCpfUseCase) {
    this.createCreditCardUseCase = createCreditCardUseCase;
    this.getCreditCardByNumberAndCustomerCpfUseCase = getCreditCardByNumberAndCustomerCpfUseCase;
    this.patchCreditCardLimitByNumberAndCustomerCpfUseCase = patchCreditCardLimitByNumberAndCustomerCpfUseCase;
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

  @PatchMapping("/{numero}/cliente/{cpf}")
  @ResponseStatus(HttpStatus.OK)
  @Override
  public void patchCreditCard(@PathVariable String numero, @PathVariable String cpf,
      @RequestBody CreditCardPaymentValueDto creditCardPaymentValueDto) {
    patchCreditCardLimitByNumberAndCustomerCpfUseCase.execute(numero, cpf, creditCardPaymentValueDto);
  }
}
