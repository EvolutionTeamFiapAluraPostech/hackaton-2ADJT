package br.com.fiap.creditcards.presentation.api;

import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_CPF;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_CVV;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_EXPIRATION_DATE;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_LIMIT;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.creditcards.infrastructure.schema.CreditCardSchema;
import br.com.fiap.creditcards.presentation.dto.CreditCardInputDto;
import br.com.fiap.creditcards.shared.annotation.DatabaseTest;
import br.com.fiap.creditcards.shared.annotation.IntegrationTest;
import br.com.fiap.creditcards.shared.api.JsonUtil;
import br.com.fiap.creditcards.shared.testdata.CreditCardTestData;
import br.com.fiap.creditcards.shared.util.StringUtil;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import jakarta.persistence.EntityManager;
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
  private static final String ALTERNATIVE_CREDIT_CARD_NUMBER = "4321567890123456";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PostCreditCardApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnInternalServerErrorWhenCpfIsNullOrEmpty(String cpf) throws Exception {
    var creditCard = new CreditCardInputDto(cpf, DEFAULT_CREDIT_CARD_LIMIT,
        DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_EXPIRATION_DATE,
        DEFAULT_CREDIT_CARD_CVV);
    var creditCardJson = JsonUtil.toJson(creditCard);

    var request = post(URL_CREDITCARDS)
        .contentType(APPLICATION_JSON)
        .content(creditCardJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @ParameterizedTest
  @ValueSource(strings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"})
  void shouldReturnInternalServerErrorWhenCpfIsInvalid(String number) throws Exception {
    var cpf = StringUtil.generateStringRepeatCharLength(number, 11);
    var creditCard = new CreditCardInputDto(cpf, DEFAULT_CREDIT_CARD_LIMIT,
        DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_EXPIRATION_DATE,
        DEFAULT_CREDIT_CARD_CVV);
    var creditCardJson = JsonUtil.toJson(creditCard);

    var request = post(URL_CREDITCARDS)
        .contentType(APPLICATION_JSON)
        .content(creditCardJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @ParameterizedTest
  @ValueSource(strings = {"72387289316", "18939181068", "12345678901", "A", "723.872.893-16"})
  void shouldReturnInternalServerErrorWhenCpfValueInvalid(String cpf) throws Exception {
    var creditCard = new CreditCardInputDto(cpf, DEFAULT_CREDIT_CARD_LIMIT,
        DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_EXPIRATION_DATE,
        DEFAULT_CREDIT_CARD_CVV);
    var creditCardJson = JsonUtil.toJson(creditCard);

    var request = post(URL_CREDITCARDS)
        .contentType(APPLICATION_JSON)
        .content(creditCardJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"-1"})
  void shouldReturnInternalServerErrorWhenLimitIsInvalid(String limit) throws Exception {
    var creditCard = new CreditCardInputDto(DEFAULT_CREDIT_CARD_CPF, limit,
        DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_EXPIRATION_DATE,
        DEFAULT_CREDIT_CARD_CVV);
    var creditCardJson = JsonUtil.toJson(creditCard);

    var request = post(URL_CREDITCARDS)
        .contentType(APPLICATION_JSON)
        .content(creditCardJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1234", "123456789012ABCD", "ABCD"})
  void shouldReturnInternalServerErrorWhenCreditCardNumberIsInvalid(String numberOfCreditCard)
      throws Exception {
    var creditCard = new CreditCardInputDto(DEFAULT_CREDIT_CARD_CPF, DEFAULT_CREDIT_CARD_LIMIT,
        numberOfCreditCard, DEFAULT_CREDIT_CARD_EXPIRATION_DATE,
        DEFAULT_CREDIT_CARD_CVV);
    var creditCardJson = JsonUtil.toJson(creditCard);

    var request = post(URL_CREDITCARDS)
        .contentType(APPLICATION_JSON)
        .content(creditCardJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1234", "123456789012ABCD", "ABCD"})
  void shouldReturnInternalServerErrorWhenExpirationDateIsInvalid(String expirationDate) throws Exception {
    var creditCard = new CreditCardInputDto(DEFAULT_CREDIT_CARD_CPF, DEFAULT_CREDIT_CARD_LIMIT,
        DEFAULT_CREDIT_CARD_NUMBER, expirationDate, DEFAULT_CREDIT_CARD_CVV);
    var creditCardJson = JsonUtil.toJson(creditCard);

    var request = post(URL_CREDITCARDS)
        .contentType(APPLICATION_JSON)
        .content(creditCardJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1234", "123456789012ABCD", "ABCD"})
  void shouldReturnInternalServerErrorWhenCvvNumberIsInvalid(String cvvNumber) throws Exception {
    var creditCard = new CreditCardInputDto(DEFAULT_CREDIT_CARD_CPF, DEFAULT_CREDIT_CARD_LIMIT,
        DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_EXPIRATION_DATE, cvvNumber);
    var creditCardJson = JsonUtil.toJson(creditCard);

    var request = post(URL_CREDITCARDS)
        .contentType(APPLICATION_JSON)
        .content(creditCardJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @Test
  void shouldReturnInternalServerErrorWhenCreditCardNumberWasAlreadyRegistered() throws Exception {
    var creditCard = new CreditCardInputDto(DEFAULT_CREDIT_CARD_CPF, DEFAULT_CREDIT_CARD_LIMIT,
        DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_CREDIT_CARD_CVV);
    var creditCardJson = JsonUtil.toJson(creditCard);

    var request = post(URL_CREDITCARDS)
        .contentType(APPLICATION_JSON)
        .content(creditCardJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @Test
  void shouldReturnForbiddenWhenCreditCardMaxQuantityWasReached() throws Exception {
    var creditCard = CreditCardTestData.createNewCreditCard();
    var creditCardSchema = CreditCardTestData.createNewCreditCardSchemaFrom(creditCard);
    creditCardSchema.setNumber("1234123456785678");
    entityManager.persist(creditCardSchema);
    var creditCardInputDto = new CreditCardInputDto(DEFAULT_CREDIT_CARD_CPF, DEFAULT_CREDIT_CARD_LIMIT,
        ALTERNATIVE_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_CREDIT_CARD_CVV);
    var creditCardJson = JsonUtil.toJson(creditCardInputDto);

    var request = post(URL_CREDITCARDS)
        .contentType(APPLICATION_JSON)
        .content(creditCardJson);
    mockMvc.perform(request)
        .andExpect(status().isForbidden());
  }

  @Test
  void shouldReturnOkWhenAllCreditCardAttributesAreOkAndCreditCardWasRegistered() throws Exception {
    var creditCard = new CreditCardInputDto(DEFAULT_CREDIT_CARD_CPF, DEFAULT_CREDIT_CARD_LIMIT,
        ALTERNATIVE_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_EXPIRATION_DATE,
        DEFAULT_CREDIT_CARD_CVV);
    var creditCardJson = JsonUtil.toJson(creditCard);

    var request = post(URL_CREDITCARDS)
        .contentType(APPLICATION_JSON)
        .content(creditCardJson);
    mockMvc.perform(request)
        .andExpect(status().isOk());

    var creditCardSchema = (CreditCardSchema) entityManager.createQuery(
            "select c from CreditCardSchema c where c.cpf = :cpf and c.number = :number")
        .setParameter("cpf", DEFAULT_CREDIT_CARD_CPF)
        .setParameter("number", DEFAULT_CREDIT_CARD_NUMBER)
        .getSingleResult();
    assertThat(creditCardSchema).isNotNull();
    assertThat(creditCardSchema.getId()).isNotNull();
    assertThat(creditCardSchema.getCpf()).isNotNull().isEqualTo(DEFAULT_CREDIT_CARD_CPF);
    assertThat(creditCardSchema.getNumber()).isNotNull().isEqualTo(DEFAULT_CREDIT_CARD_NUMBER);
    assertThat(creditCardSchema.getLimitValue()).isNotNull().isEqualTo(DEFAULT_CREDIT_CARD_LIMIT);
    assertThat(creditCardSchema.getExpirationDate()).isNotNull()
        .isEqualTo(DEFAULT_CREDIT_CARD_EXPIRATION_DATE);
    assertThat(creditCardSchema.getCvv()).isNotNull().isEqualTo(DEFAULT_CREDIT_CARD_CVV);
  }
}
