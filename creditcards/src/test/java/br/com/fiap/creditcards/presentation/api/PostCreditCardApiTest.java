package br.com.fiap.creditcards.presentation.api;

import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_CVV;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_EXPIRATION_DATE;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_LIMIT;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_NUMBER;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.creditcards.presentation.dto.CreditCardInputDto;
import br.com.fiap.creditcards.shared.annotation.DatabaseTest;
import br.com.fiap.creditcards.shared.annotation.IntegrationTest;
import br.com.fiap.creditcards.shared.api.JsonUtil;
import br.com.fiap.creditcards.shared.util.StringUtil;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
@WireMockTest(httpPort = 7070)
class PostCreditCardApiTest {

  private static final String URL_CREDITCARDS = "/api/cartao";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PostCreditCardApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCpfIsNullOrEmpty(String cpf) throws Exception {
    var creditCard = new CreditCardInputDto(cpf, DEFAULT_CREDIT_CARD_LIMIT,
        DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_EXPIRATION_DATE,
        DEFAULT_CREDIT_CARD_CVV);
    var creditCardJson = JsonUtil.toJson(creditCard);

    var request = post(URL_CREDITCARDS)
        .contentType(APPLICATION_JSON)
        .content(creditCardJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @ValueSource(strings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"})
  void shouldReturnBadRequestWhenCpfIsInvalid(String number) {
    var cpf = StringUtil.generateStringRepeatCharLength(number, 11);
    Assertions.fail("shouldReturnBadRequestWhenCpfIsInvalid");
  }

  @ParameterizedTest
  @ValueSource(strings = {"72387289316", "18939181068", "12345678901", "A", "723.872.893-16"})
  void shouldReturnBadRequestWhenCpfValueInvalid(String cpfValue) {
    Assertions.fail("shouldReturnBadRequestWhenCpfValueInvalid");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"0", "-1"})
  void shouldReturnBadRequestWhenLimitIsInvalid(String limit) {
    Assertions.fail("shouldReturnBadRequestWhenLimitIsInvalid");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1234", "123456789012ABCD", "ABCD"})
  void shouldReturnBadRequestWhenCreditCardNumberIsInvalid(String numberOfCreditCard) {
    Assertions.fail("shouldReturnBadRequestWhenCreditCardNumberIsInvalid");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1234", "123456789012ABCD", "ABCD"})
  void shouldReturnBadRequestWhenExpirationDateIsInvalid(String numberOfCreditCard) {
    Assertions.fail("shouldReturnBadRequestWhenExpirationDateIsInvalid");
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1234", "123456789012ABCD", "ABCD"})
  void shouldReturnBadRequestWhenCvvNumberIsInvalid(String cvvNumber) {
    Assertions.fail("shouldReturnBadRequestWhenCvvNumberIsInvalid");
  }

  @Test
  void shouldReturnOkWhenCreditCardWasRegistered() {
    Assertions.fail("shouldReturnOkWhenCreditCardWasRegistered");
  }
}
