package br.com.fiap.customers.infrastructure.usecase;

import br.com.fiap.customers.application.gateway.CustomerGateway;
import br.com.fiap.customers.application.usecase.CreateCustomerUseCase;
import br.com.fiap.customers.application.validator.CustomerSingleCpfValidator;
import br.com.fiap.customers.domain.entity.Customer;
import br.com.fiap.customers.presentation.dto.CustomerInputDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateCustomerInteractor implements CreateCustomerUseCase {

  private final CustomerGateway customerGateway;
  private final CustomerSingleCpfValidator customerSingleCpfValidator;

  public CreateCustomerInteractor(CustomerGateway customerGateway,
      CustomerSingleCpfValidator customerSingleCpfValidator) {
    this.customerGateway = customerGateway;
    this.customerSingleCpfValidator = customerSingleCpfValidator;
  }

  @Transactional
  @Override
  public Customer execute(CustomerInputDto customerInputDto) {
    var customer = new Customer(customerInputDto.nome(), customerInputDto.cpf(),
        customerInputDto.email(), customerInputDto.telefone(), customerInputDto.rua(),
        customerInputDto.cidade(), customerInputDto.estado(), customerInputDto.pais(),
        customerInputDto.cep());
    customerSingleCpfValidator.validate(customerInputDto.cpf());
    return customerGateway.save(customer);
  }
}
