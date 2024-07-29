package br.com.fiap.customers.shared.testdata;

import br.com.fiap.customers.domain.entity.Customer;
import br.com.fiap.customers.infrastructure.schema.CustomerSchema;
import br.com.fiap.customers.presentation.dto.CustomerInputDto;
import java.util.UUID;

public final class CustomerTestData {

  public static final String DEFAULT_CUSTOMER_ID = "dcd3398e-4988-4fba-b8c0-a649ae1ff677";
  public static final UUID DEFAULT_CUSTOMER_UUID = UUID.fromString(DEFAULT_CUSTOMER_ID);
  public static final String DEFAULT_CUSTOMER_NAME = "Morpheus";
  public static final String DEFAULT_CUSTOMER_CPF = "18939181069";
  public static final String DEFAULT_CUSTOMER_CPF_ALREADY_POPULATED_IN_DATABASE = "84527263846";
  public static final String DEFAULT_CUSTOMER_EMAIL = "morpheus@matrix.com";
  public static final String DEFAULT_CUSTOMER_PHONE_NUMBER = "5511912345678";
  public static final String DEFAULT_CUSTOMER_STREET = "Av. Lins de Vasconcelos";
  public static final String DEFAULT_CUSTOMER_CITY = "São Paulo";
  public static final String DEFAULT_CUSTOMER_STATE = "São Paulo";
  public static final String DEFAULT_CUSTOMER_COUNTRY = "Brasil";
  public static final String DEFAULT_CUSTOMER_POSTAL_CODE = "01538001";

  public static CustomerInputDto createCustomerInputDto() {
    return new CustomerInputDto(DEFAULT_CUSTOMER_CPF, DEFAULT_CUSTOMER_NAME,
        DEFAULT_CUSTOMER_EMAIL,DEFAULT_CUSTOMER_PHONE_NUMBER,DEFAULT_CUSTOMER_STREET,
        DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_POSTAL_CODE, DEFAULT_CUSTOMER_COUNTRY);
  }

  public static Customer createNewCustomer() {
    return new Customer(DEFAULT_CUSTOMER_NAME, DEFAULT_CUSTOMER_CPF, DEFAULT_CUSTOMER_EMAIL,
        DEFAULT_CUSTOMER_PHONE_NUMBER,DEFAULT_CUSTOMER_STREET,
        DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY, DEFAULT_CUSTOMER_POSTAL_CODE);
  }

  public static Customer createCustomer() {
    return new Customer(DEFAULT_CUSTOMER_ID, DEFAULT_CUSTOMER_NAME, DEFAULT_CUSTOMER_CPF,
        DEFAULT_CUSTOMER_EMAIL,DEFAULT_CUSTOMER_PHONE_NUMBER,DEFAULT_CUSTOMER_STREET,
        DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY, DEFAULT_CUSTOMER_POSTAL_CODE);
  }

  public static CustomerSchema createCustomerSchemaFrom(Customer customer) {
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

  public static CustomerSchema createCustomerSchemaPersistedFrom(Customer customer) {
    var customerSchema = createCustomerSchemaFrom(customer);
    customerSchema.setId(DEFAULT_CUSTOMER_UUID);
    return customerSchema;
  }

  private CustomerTestData(){
  }
}
