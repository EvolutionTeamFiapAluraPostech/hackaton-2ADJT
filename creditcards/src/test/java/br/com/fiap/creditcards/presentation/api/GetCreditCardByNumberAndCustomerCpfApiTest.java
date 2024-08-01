package br.com.fiap.creditcards.presentation.api;

import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_CPF;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_ID;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_NUMBER;
import static br.com.fiap.creditcards.shared.util.IsUUID.isUUID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.creditcards.shared.annotation.DatabaseTest;
import br.com.fiap.creditcards.shared.annotation.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class GetCreditCardByNumberAndCustomerCpfApiTest {

  private static final String URL_CREDIT_CARDS = "/api/cartao/{numero}/cliente/{cpf}";
  private static final String ALTERNATIVE_CREDIT_CARD_NUMBER = "4321567890123456";
  private final MockMvc mockMvc;

  @Autowired
  GetCreditCardByNumberAndCustomerCpfApiTest(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"72387289316", "18939181068", "12345678901", "A", "723.872.893-16"})
  void shouldReturnInternalServerErrorWhenCpfNumberIsInvalid(String cpf) throws Exception {
    var request = get(URL_CREDIT_CARDS, DEFAULT_CREDIT_CARD_NUMBER, cpf);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @Test
  void shouldReturnNotFoundWhenCreditCardWasNotFound() throws Exception {
    var request = get(URL_CREDIT_CARDS, ALTERNATIVE_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_CPF);
    mockMvc.perform(request)
        .andExpect(status().isNotFound());
  }

  @Test
  void shouldReturnOkWhenCreditCardWasFound() throws Exception {
    var request = get(URL_CREDIT_CARDS, DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_CPF);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id", isUUID()))
        .andExpect(jsonPath("$.id", equalTo(DEFAULT_CREDIT_CARD_ID)));
  }
}
