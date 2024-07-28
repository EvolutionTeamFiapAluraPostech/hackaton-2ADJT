package br.com.fiap.customers.infrastructure.validator;

import br.com.fiap.customers.application.gateway.CustomerGateway;
import br.com.fiap.customers.application.validator.CustomerSingleCpfValidator;
import br.com.fiap.customers.domain.exception.DuplicatedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.FieldError;

@Component
public class CustomerSchemaSingleCpfValidator implements CustomerSingleCpfValidator {

  public static final String CUSTOMER_CPF_FIELD = "CPF";
  public static final String CUSTOMER_CPF_ALREADY_EXISTS_MESSAGE = "Customer CPF already exists. %s";
  private final CustomerGateway customerGateway;

  public CustomerSchemaSingleCpfValidator(CustomerGateway customerGateway) {
    this.customerGateway = customerGateway;
  }

  public void validate(String cpf) {
    var customer = customerGateway.findByCpf(cpf);
    if (customer != null) {
      throw new DuplicatedException(new FieldError(this.getClass().getSimpleName(),
          CUSTOMER_CPF_FIELD,
          CUSTOMER_CPF_ALREADY_EXISTS_MESSAGE.formatted(cpf)));
    }
  }
}
