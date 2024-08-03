package br.com.fiap.payments.infrastructure.validator;

import br.com.fiap.payments.application.validator.CreditCardExistsValidator;
import br.com.fiap.payments.infrastructure.httpclient.creditcard.CreditCardHttpClient;
import org.springframework.stereotype.Component;

@Component
public class CreditCardExistsInCreditCardsMicroserviceValidator implements
    CreditCardExistsValidator {

  private final CreditCardHttpClient creditCardHttpClient;

  public CreditCardExistsInCreditCardsMicroserviceValidator(
      CreditCardHttpClient creditCardHttpClient) {
    this.creditCardHttpClient = creditCardHttpClient;
  }

  @Override
  public void validate(String number, String cpf) {
    creditCardHttpClient.getCreditCardByNumberAndCpf(number, cpf);
  }
}
