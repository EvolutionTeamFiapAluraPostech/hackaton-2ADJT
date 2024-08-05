package br.com.fiap.payments.infrastructure.usecase;

import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CPF;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.createPayment;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.fiap.payments.application.gateway.PaymentGateway;
import br.com.fiap.payments.shared.testdata.PaymentsTestData;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetPaymentsByCustomerCpfInteractorTest {

  @Mock
  private PaymentGateway paymentGateway;
  @InjectMocks
  private GetPaymentsByCustomerCpfInteractor getPaymentsByCustomerCpfInteractor;

  @Test
  void shouldReturnListOfPaymentsWhenPaymentsWasFoundByCpf() {
    var payment = createPayment();
    when(paymentGateway.findByCpf(PaymentsTestData.DEFAULT_PAYMENT_CPF)).thenReturn(
        List.of(payment));

    var payments = getPaymentsByCustomerCpfInteractor.execute(DEFAULT_PAYMENT_CPF);

    assertThat(payments).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldReturnEmptyListOfPaymentsWhenPaymentsWasNotFoundByCpf() {
    when(paymentGateway.findByCpf(PaymentsTestData.DEFAULT_PAYMENT_CPF)).thenReturn(
        Collections.emptyList());

    var payments = getPaymentsByCustomerCpfInteractor.execute(DEFAULT_PAYMENT_CPF);

    assertThat(payments).isEmpty();
  }
}
