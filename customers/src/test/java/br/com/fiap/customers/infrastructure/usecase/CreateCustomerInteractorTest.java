package br.com.fiap.customers.infrastructure.usecase;

import static br.com.fiap.customers.shared.testdata.CustomerTestData.createCustomer;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.createCustomerInputDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.customers.application.gateway.CustomerGateway;
import br.com.fiap.customers.domain.entity.Customer;
import br.com.fiap.customers.domain.exception.DuplicatedException;
import br.com.fiap.customers.infrastructure.validator.CustomerSchemaSingleCpfValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreateCustomerInteractorTest {

  @Mock
  private CustomerGateway customerGateway;
  @Mock
  private CustomerSchemaSingleCpfValidator customerSchemaSingleCpfValidator;
  @InjectMocks
  private CreateCustomerInteractor createCustomerInteractor;

  @Test
  void shouldCreateCustomer() {
    var customerInputDto = createCustomerInputDto();
    var customerSaved = createCustomer();
    doNothing().when(customerSchemaSingleCpfValidator).validate(customerInputDto.cpf());
    when(customerGateway.save(any(Customer.class))).thenReturn(customerSaved);

    var customer = createCustomerInteractor.execute(customerInputDto);

    assertThat(customer).isNotNull();
    assertThat(customer.getId()).isNotNull();
  }

  @Test
  void shouldThrowExceptionWhenCpfAlreadyExists() {
    var customerInputDto = createCustomerInputDto();
    doThrow(DuplicatedException.class).when(customerSchemaSingleCpfValidator)
        .validate(customerInputDto.cpf());

    assertThatThrownBy(() -> createCustomerInteractor.execute(customerInputDto))
        .isInstanceOf(DuplicatedException.class);

    verify(customerGateway, never()).save(any(Customer.class));
  }
}
