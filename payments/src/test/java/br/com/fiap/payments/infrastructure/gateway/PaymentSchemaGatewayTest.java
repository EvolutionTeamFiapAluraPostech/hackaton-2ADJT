package br.com.fiap.payments.infrastructure.gateway;

import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CPF;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.createNewPayment;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.createPayment;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.createPaymentSchema;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import br.com.fiap.payments.infrastructure.repository.PaymentSchemaRepository;
import br.com.fiap.payments.infrastructure.schema.PaymentSchema;
import br.com.fiap.payments.shared.testdata.PaymentsTestData;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentSchemaGatewayTest {

  @Mock
  private PaymentSchemaRepository paymentSchemaRepository;
  @InjectMocks
  private PaymentSchemaGateway paymentSchemaGateway;

  @Test
  void shouldSavePaymentWhenAllAttributesAreCorrect() {
    var payment = createNewPayment();
    var paymentWithId = createPayment();
    var paymentSchemaWithId = createPaymentSchema(paymentWithId);
    when(paymentSchemaRepository.save(any(PaymentSchema.class))).thenReturn(paymentSchemaWithId);

    var paymentCreated = paymentSchemaGateway.save(payment);

    assertThat(paymentCreated).isNotNull();
    assertThat(paymentCreated).usingRecursiveComparison().isEqualTo(paymentWithId);
  }

  @Test
  void shouldReturnListOfPaymentsWhenPaymentsWasFoundByCpf() {
    var payment = createPayment();
    var paymentSchema = createPaymentSchema(payment);
    when(paymentSchemaRepository.findByCpf(PaymentsTestData.DEFAULT_PAYMENT_CPF)).thenReturn(
        List.of(paymentSchema));

    var payments = paymentSchemaGateway.findByCpf(DEFAULT_PAYMENT_CPF);

    assertThat(payments).isNotEmpty().hasSize(1);
  }

  @Test
  void shouldReturnEmptyListOfPaymentsWhenPaymentsWasNotFoundByCpf() {
    when(paymentSchemaRepository.findByCpf(PaymentsTestData.DEFAULT_PAYMENT_CPF)).thenReturn(
        Collections.emptyList());

    var payments = paymentSchemaGateway.findByCpf(DEFAULT_PAYMENT_CPF);

    assertThat(payments).isEmpty();
  }
}
