package br.com.fiap.customers.infrastructure.validator;

import static br.com.fiap.customers.infrastructure.validator.CustomerSchemaSingleCpfValidator.CUSTOMER_CPF_ALREADY_EXISTS_MESSAGE;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CPF;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.createCustomer;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.fiap.customers.application.gateway.CustomerGateway;
import br.com.fiap.customers.domain.exception.DuplicatedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CustomerSchemaSingleCpfValidatorTest {

  @Mock
  private CustomerGateway customerGateway;
  @InjectMocks
  private CustomerSchemaSingleCpfValidator customerSchemaSingleCpfValidator;

  @Test
  void shouldValidateWhenCpfDoesNotExist() {
    when(customerGateway.findByCpf(DEFAULT_CUSTOMER_CPF)).thenReturn(null);

    assertThatCode(() -> customerSchemaSingleCpfValidator.validate(
        DEFAULT_CUSTOMER_CPF)).doesNotThrowAnyException();
  }

  @Test
  void shouldThrowDuplicatedExceptionWhenCpfAlreadyExists() {
    var customer = createCustomer();
    when(customerGateway.findByCpf(customer.getCpf())).thenReturn(customer);

    assertThatThrownBy(() -> customerSchemaSingleCpfValidator.validate(
        customer.getCpf()))
        .isInstanceOf(DuplicatedException.class)
        .hasMessage(CUSTOMER_CPF_ALREADY_EXISTS_MESSAGE.formatted(customer.getCpf()));
  }
}
