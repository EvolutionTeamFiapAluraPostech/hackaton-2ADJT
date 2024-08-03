package br.com.fiap.creditcards.presentation.api;

import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_CPF;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_NUMBER;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.creditcards.presentation.dto.CreditCardPaymentValueDto;
import br.com.fiap.creditcards.shared.annotation.DatabaseTest;
import br.com.fiap.creditcards.shared.annotation.IntegrationTest;
import br.com.fiap.creditcards.shared.api.JsonUtil;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class PatchCreditCardApiTest {

  private static final String URL_CREDITCARDS = "/api/cartao/{numero}/cliente/{cpf}";
  private final MockMvc mockMvc;

  @Autowired
  PatchCreditCardApiTest(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  @Test
  void shouldReturnOkWhenCreditCardLimitWasUpdated() throws Exception {
    var creditCardPaymentValueDto = new CreditCardPaymentValueDto(new BigDecimal("350.00"));
    var creditCardPaymentValueJson = JsonUtil.toJson(creditCardPaymentValueDto);

    var request = patch(URL_CREDITCARDS, DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_CPF)
        .contentType(APPLICATION_JSON)
        .content(creditCardPaymentValueJson);
    mockMvc.perform(request)
        .andExpect(status().isOk());
  }
}
