package br.com.fiap.payments.presentation.api;

import static br.com.fiap.payments.shared.testdata.PaymentsTestData.ALTERNATIVE_PAYMENT_CPF;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.ALTERNATIVE_PAYMENT_CREDIT_CARD_CVV;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.ALTERNATIVE_PAYMENT_CREDIT_CARD_EXPIRATION_DATE;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.ALTERNATIVE_PAYMENT_CREDIT_CARD_NUMBER;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.ALTERNATIVE_PAYMENT_VALUE;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CPF;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CREDIT_CARD_CVV;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CREDIT_CARD_NUMBER;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_VALUE;
import static br.com.fiap.payments.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.payments.infrastructure.schema.PaymentSchema;
import br.com.fiap.payments.presentation.dto.PaymentInputDto;
import br.com.fiap.payments.shared.annotation.DatabaseTest;
import br.com.fiap.payments.shared.annotation.IntegrationTest;
import br.com.fiap.payments.shared.api.JsonUtil;
import br.com.fiap.payments.shared.util.StringUtil;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTest
@DatabaseTest
class PostPaymentApiTest {

  private static final String URL_PAYMENTS = "/api/pagamentos";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PostPaymentApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"72387289316", "18939181068", "12345678901", "A", "723.872.893-16"})
  void shouldReturnInternalServerErrorWhenCpfIsInvalid(String cpf) throws Exception {
    var paymentInputDto = new PaymentInputDto(cpf, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE);
    var paymentJson = JsonUtil.toJson(paymentInputDto);

    var request = post(URL_PAYMENTS)
        .contentType(APPLICATION_JSON)
        .content(paymentJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @ParameterizedTest
  @ValueSource(strings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"})
  void shouldReturnInternalServerErrorWhenCpfLengthIsInvalid(String number) throws Exception {
    var cpf = StringUtil.generateStringRepeatCharLength(number, 11);
    var paymentInputDto = new PaymentInputDto(cpf, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE);
    var paymentJson = JsonUtil.toJson(paymentInputDto);

    var request = post(URL_PAYMENTS)
        .contentType(APPLICATION_JSON)
        .content(paymentJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1234", "123456789012ABCD", "ABCD"})
  void shouldReturnInternalServerErrorWhenCreditCardNumberIsInvalid(String creditCardNumber)
      throws Exception {
    var paymentInputDto = new PaymentInputDto(DEFAULT_PAYMENT_CPF, creditCardNumber,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE);
    var paymentJson = JsonUtil.toJson(paymentInputDto);

    var request = post(URL_PAYMENTS)
        .contentType(APPLICATION_JSON)
        .content(paymentJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1234", "123456789012ABCD", "ABCD"})
  void shouldReturnInternalServerErrorWhenCreditCardExpirationDateIsInvalid(
      String creditCardExpirationDate) throws Exception {
    var paymentInputDto = new PaymentInputDto(DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        creditCardExpirationDate, DEFAULT_PAYMENT_CREDIT_CARD_CVV, DEFAULT_PAYMENT_VALUE);
    var paymentJson = JsonUtil.toJson(paymentInputDto);

    var request = post(URL_PAYMENTS)
        .contentType(APPLICATION_JSON)
        .content(paymentJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1234", "123456789012ABCD", "ABCD"})
  void shouldReturnInternalServerErrorWhenCvvNumberIsInvalid(String cvvNumber) throws Exception {
    var paymentInputDto = new PaymentInputDto(DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, cvvNumber, DEFAULT_PAYMENT_VALUE);
    var paymentJson = JsonUtil.toJson(paymentInputDto);

    var request = post(URL_PAYMENTS)
        .contentType(APPLICATION_JSON)
        .content(paymentJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"-1", "0"})
  void shouldReturnInternalServerErrorWhenValueIsInvalid(String value) throws Exception {
    var paymentInputDto = new PaymentInputDto(DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV, value);
    var paymentJson = JsonUtil.toJson(paymentInputDto);

    var request = post(URL_PAYMENTS)
        .contentType(APPLICATION_JSON)
        .content(paymentJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @Test
  void shouldReturnInternalServerErrorWhenCreditCardDoesNotExist() throws Exception {
    var paymentInputDto = new PaymentInputDto(ALTERNATIVE_PAYMENT_CPF,
        ALTERNATIVE_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE);
    var paymentJson = JsonUtil.toJson(paymentInputDto);

    var request = post(URL_PAYMENTS)
        .contentType(APPLICATION_JSON)
        .content(paymentJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @Test
  void shouldReturnInternalServerErrorWhenCreditCardExpirationDateIsInvalid() throws Exception {
    var paymentInputDto = new PaymentInputDto(DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        ALTERNATIVE_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE);
    var paymentJson = JsonUtil.toJson(paymentInputDto);

    var request = post(URL_PAYMENTS)
        .contentType(APPLICATION_JSON)
        .content(paymentJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @Test
  void shouldReturnInternalServerErrorWhenCreditCardCvvIsInvalid() throws Exception {
    var paymentInputDto = new PaymentInputDto(DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, ALTERNATIVE_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE);
    var paymentJson = JsonUtil.toJson(paymentInputDto);

    var request = post(URL_PAYMENTS)
        .contentType(APPLICATION_JSON)
        .content(paymentJson);
    mockMvc.perform(request)
        .andExpect(status().isInternalServerError());
  }

  @Test
  void shouldReturnPaymentRequiredWhenInsufficientCreditCardLimit() throws Exception {
    var paymentInputDto = new PaymentInputDto(DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        ALTERNATIVE_PAYMENT_VALUE);
    var paymentJson = JsonUtil.toJson(paymentInputDto);

    var request = post(URL_PAYMENTS)
        .contentType(APPLICATION_JSON)
        .content(paymentJson);
    mockMvc.perform(request)
        .andExpect(status().isPaymentRequired());
  }

  @Test
  void shouldReturnOkWhenAllPaymentsAttributesAreCorrect() throws Exception {
    var paymentInputDto = new PaymentInputDto(DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        ALTERNATIVE_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE);
    var paymentJson = JsonUtil.toJson(paymentInputDto);

    var request = post(URL_PAYMENTS)
        .contentType(APPLICATION_JSON)
        .content(paymentJson);
    var result = mockMvc.perform(request)
        .andExpect(status().isPaymentRequired())
        .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.chave_pagamento", isUUID()))
        .andReturn();

    var contentAsString = result.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.chave_pagamento").toString();
    var paymentFound = entityManager.find(PaymentSchema.class, UUID.fromString(id));
    assertThat(paymentFound).isNotNull();
    Assertions.fail("shouldReturnOkWhenAllPaymentsAttributesAreCorrect");
  }
}
