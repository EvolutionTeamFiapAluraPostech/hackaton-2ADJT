package br.com.fiap.payments.presentation.api;

import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CPF;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.createNewPayment;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.createNewPaymentSchema;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.payments.shared.annotation.DatabaseTest;
import br.com.fiap.payments.shared.annotation.IntegrationTest;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetPaymentsByCustomerCpfApiTest {

  private static final String URL_PAYMENTS = "/api/pagamentos/cliente/{cpf}";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  GetPaymentsByCustomerCpfApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  @Test
  void shouldReturnOkWhenPaymentsWasFound() throws Exception {
    var payment = createNewPayment();
    var paymentSchema = createNewPaymentSchema(payment);
    entityManager.persist(paymentSchema);

    var request = get(URL_PAYMENTS, DEFAULT_PAYMENT_CPF);
    mockMvc.perform(request).andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON));
  }
}
