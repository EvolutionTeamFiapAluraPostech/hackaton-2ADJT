package br.com.fiap.customers.application.gateway;

import br.com.fiap.customers.domain.entity.Customer;

public interface CustomerGateway {

  Customer save(Customer customer);

  Customer findByCpf(String cpf);

  Customer findByCpfRequired(String cpf);
}
