package br.com.fiap.creditcards.infrastructure.validator;

import br.com.fiap.creditcards.application.validator.CustomerExistsValidator;
import br.com.fiap.creditcards.infrastructure.httpclient.customer.CustomerHttpClient;
import org.springframework.stereotype.Component;

@Component
public class CustomerSchemaExistsValidator implements CustomerExistsValidator {

  private final CustomerHttpClient customerHttpClient;

  public CustomerSchemaExistsValidator(CustomerHttpClient customerHttpClient) {
    this.customerHttpClient = customerHttpClient;
  }

  @Override
  public void validate(String cpf) {
    customerHttpClient.getCustomerByCpf(cpf);
  }
}
