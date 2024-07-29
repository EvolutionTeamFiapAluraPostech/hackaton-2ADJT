package br.com.fiap.customers.infrastructure.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.fiap.customers.application.gateway.CustomerGateway;
import br.com.fiap.customers.domain.exception.ValidatorException;
import br.com.fiap.customers.shared.testdata.CustomerTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetCustomerByCpfInteractorTest {

  @Mock
  private CustomerGateway customerGateway;
  @InjectMocks
  private GetCustomerByCpfInteractor getCustomerByCpfInteractor;

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"72387289316", "189391810Ba", "123.456.789-01"})
  void shouldThrowValidatorExceptionWhenCpfIsInvalid(String cpf) {
    assertThatThrownBy(() -> getCustomerByCpfInteractor.execute(cpf))
        .isInstanceOf(ValidatorException.class);
  }

  @Test
  void shouldGetCustomerWhenCustomerWasFoundByCpf() {
    var customer = CustomerTestData.createCustomer();
    when(customerGateway.findByCpf(customer.getCpf())).thenReturn(customer);

    var customerFoundByCpf = getCustomerByCpfInteractor.execute(customer.getCpf());

    assertThat(customerFoundByCpf).isNotNull();
    assertThat(customerFoundByCpf).usingRecursiveComparison().isEqualTo(customer);
  }
}
