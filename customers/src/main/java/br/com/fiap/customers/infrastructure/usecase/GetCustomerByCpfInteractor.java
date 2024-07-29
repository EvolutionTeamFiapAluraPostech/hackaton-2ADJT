package br.com.fiap.customers.infrastructure.usecase;

import br.com.fiap.customers.application.gateway.CustomerGateway;
import br.com.fiap.customers.application.usecase.GetCustomerByCpfUseCase;
import br.com.fiap.customers.domain.entity.Customer;
import br.com.fiap.customers.domain.valueobject.Cpf;
import org.springframework.stereotype.Service;

@Service
public class GetCustomerByCpfInteractor implements GetCustomerByCpfUseCase {

  private final CustomerGateway customerGateway;

  public GetCustomerByCpfInteractor(CustomerGateway customerGateway) {
    this.customerGateway = customerGateway;
  }

  @Override
  public Customer execute(String cpfParam) {
    var cpf = new Cpf(cpfParam);
    return customerGateway.findByCpf(cpf.getCpfValue());
  }
}
