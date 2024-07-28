package br.com.fiap.customers.infrastructure.gateway;

import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CPF;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.createCustomer;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.createCustomerSchemaPersistedFrom;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.createNewCustomer;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import br.com.fiap.customers.infrastructure.repository.CustomerSchemaRepository;
import br.com.fiap.customers.infrastructure.schema.CustomerSchema;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerSchemaGatewayTest {

  @Mock
  private CustomerSchemaRepository customerSchemaRepository;
  @InjectMocks
  private CustomerSchemaGateway customerSchemaGateway;

  @Test
  void shouldSaveCustomerWhenCustomerAttributesAreCorrect() {
    var customer = createNewCustomer();
    var customerSchemaPersisted = createCustomerSchemaPersistedFrom(customer);
    when(customerSchemaRepository.save(any(CustomerSchema.class))).thenReturn(
        customerSchemaPersisted);

    var customerSchema = customerSchemaGateway.save(customer);

    assertThat(customerSchema).isNotNull();
    assertThat(customerSchema.getId()).isNotNull();
    assertThat(customerSchema).usingRecursiveComparison().ignoringFields("id").isEqualTo(customer);
  }

  @Test
  void shouldFindCustomerByCpf() {
    var customer = createCustomer();
    var customerSchema = createCustomerSchemaPersistedFrom(customer);
    when(customerSchemaRepository.findByCpf(customer.getCpf())).thenReturn(
        Optional.of(customerSchema));

    var customerFound = customerSchemaGateway.findByCpf(customer.getCpf());

    assertThat(customerFound).isNotNull();
    assertThat(customerFound.getId()).isNotNull();
    assertThat(customerFound).usingRecursiveComparison().isEqualTo(customer);
  }

  @Test
  void shouldReturnNullWhenCustomerWasNotFoundByCpf() {
    when(customerSchemaRepository.findByCpf(DEFAULT_CUSTOMER_CPF)).thenReturn(Optional.empty());

    var customerFound = customerSchemaGateway.findByCpf(DEFAULT_CUSTOMER_CPF);

    assertThat(customerFound).isNull();
  }
}
