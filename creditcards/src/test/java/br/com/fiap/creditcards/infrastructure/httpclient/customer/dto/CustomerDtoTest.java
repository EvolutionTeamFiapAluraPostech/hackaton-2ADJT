package br.com.fiap.creditcards.infrastructure.httpclient.customer.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class CustomerDtoTest {

  public static final String CUSTOMER_ID = "dcd3398e-4988-4fba-b8c0-a649ae1ff677";

  @Test
  void shouldCreateCustomerDto() {
    var customerDto = new CustomerDto(CUSTOMER_ID);

    assertThat(customerDto).isNotNull();
    assertThat(customerDto.id_cliente()).isNotNull().isEqualTo(CUSTOMER_ID);
  }

}