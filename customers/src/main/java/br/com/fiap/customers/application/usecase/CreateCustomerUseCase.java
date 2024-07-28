package br.com.fiap.customers.application.usecase;

import br.com.fiap.customers.domain.entity.Customer;
import br.com.fiap.customers.presentation.dto.CustomerInputDto;

public interface CreateCustomerUseCase {

  Customer execute(CustomerInputDto customerInputDto);
}
