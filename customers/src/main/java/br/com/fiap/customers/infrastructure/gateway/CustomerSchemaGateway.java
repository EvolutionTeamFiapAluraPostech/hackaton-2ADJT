package br.com.fiap.customers.infrastructure.gateway;

import br.com.fiap.customers.application.gateway.CustomerGateway;
import br.com.fiap.customers.domain.entity.Customer;
import br.com.fiap.customers.infrastructure.repository.CustomerSchemaRepository;
import br.com.fiap.customers.infrastructure.schema.CustomerSchema;
import org.springframework.stereotype.Service;

@Service
public class CustomerSchemaGateway implements CustomerGateway {

  private final CustomerSchemaRepository customerSchemaRepository;

  public CustomerSchemaGateway(CustomerSchemaRepository customerSchemaRepository) {
    this.customerSchemaRepository = customerSchemaRepository;
  }

  @Override
  public Customer save(Customer customer) {
    var customerSchema = createNewCustomerSchemaFrom(customer);
    var customerSchemaSaved = customerSchemaRepository.save(customerSchema);
    return getCustomerFrom(customerSchemaSaved);
  }

  @Override
  public Customer findByCpf(String cpf) {
    var customerSchema = customerSchemaRepository.findByCpf(cpf);
    return customerSchema.map(this::getCustomerFrom).orElse(null);
  }

  private Customer getCustomerFrom(CustomerSchema customerSchemaSaved) {
    return new Customer(customerSchemaSaved.getId().toString(),
        customerSchemaSaved.getName(), customerSchemaSaved.getCpf(), customerSchemaSaved.getEmail(),
        customerSchemaSaved.getPhoneNumber(), customerSchemaSaved.getStreet(),
        customerSchemaSaved.getCity(), customerSchemaSaved.getState(),
        customerSchemaSaved.getCountry(), customerSchemaSaved.getPostalCode());
  }

  private CustomerSchema createNewCustomerSchemaFrom(Customer customer) {
    return CustomerSchema.builder()
        .name(customer.getName())
        .cpf(customer.getCpf())
        .email(customer.getEmail())
        .phoneNumber(customer.getPhoneNumber())
        .street(customer.getStreet())
        .city(customer.getCity())
        .state(customer.getState())
        .country(customer.getCountry())
        .postalCode(customer.getPostalCode())
        .build();
  }
}
