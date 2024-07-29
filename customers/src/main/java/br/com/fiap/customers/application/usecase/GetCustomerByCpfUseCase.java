package br.com.fiap.customers.application.usecase;

import br.com.fiap.customers.domain.entity.Customer;

public interface GetCustomerByCpfUseCase {

  Customer execute(String cpfParam);
}
