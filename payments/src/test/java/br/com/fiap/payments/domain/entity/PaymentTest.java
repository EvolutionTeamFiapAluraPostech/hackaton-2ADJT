package br.com.fiap.payments.domain.entity;

import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CPF;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CREDIT_CARD_CVV;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CREDIT_CARD_NUMBER;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_STATUS_APROVADO;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_VALUE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.payments.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class PaymentTest {

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"A", "1", "11111111111", "1234567890", "123456789012"})
  void shouldThrowValidatorExceptionWhenCpfIsInvalid(String cpf) {
    assertThatThrownBy(() -> new Payment(cpf, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE, DEFAULT_PAYMENT_STATUS_APROVADO)).isInstanceOf(
        ValidatorException.class);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1", "12345678901234567"})
  void shouldThrowValidatorExceptionWhenPaymentNumberIsInvalid(String number) {
    assertThatThrownBy(() -> new Payment(DEFAULT_PAYMENT_CPF, number,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE, DEFAULT_PAYMENT_STATUS_APROVADO)).isInstanceOf(
        ValidatorException.class);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1", "12/2024"})
  void shouldThrowValidatorExceptionWhenExpirationDateIsInvalid(String expirationDate) {
    assertThatThrownBy(
        () -> new Payment(DEFAULT_PAYMENT_CPF, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER, expirationDate,
            DEFAULT_PAYMENT_CREDIT_CARD_CVV, DEFAULT_PAYMENT_VALUE,
            DEFAULT_PAYMENT_STATUS_APROVADO))
        .isInstanceOf(ValidatorException.class);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1", "1234"})
  void shouldThrowValidatorExceptionWhenCvvIsInvalid(String cvv) {
    assertThatThrownBy(
        () -> new Payment(DEFAULT_PAYMENT_CPF, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
            DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, cvv, DEFAULT_PAYMENT_VALUE,
            DEFAULT_PAYMENT_STATUS_APROVADO))
        .isInstanceOf(ValidatorException.class);
  }

  @ParameterizedTest
  @ValueSource(strings = {"-1"})
  void shouldThrowValidatorExceptionWhenAccountValueIsInvalid(String accountValue) {
    assertThatThrownBy(
        () -> new Payment(DEFAULT_PAYMENT_CPF, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
            DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
            accountValue, DEFAULT_PAYMENT_STATUS_APROVADO))
        .isInstanceOf(ValidatorException.class);
  }

  @Test
  void shouldCreatePaymentWhenAllAttributesAreCorrect() {
    var creditCard = new Payment(DEFAULT_PAYMENT_CPF, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE, DEFAULT_PAYMENT_STATUS_APROVADO);

    assertThat(creditCard).isNotNull();
    assertThat(creditCard.getCpf()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_PAYMENT_CPF);
    assertThat(creditCard.getNumber()).isNotNull().isNotEmpty()
        .isEqualTo(DEFAULT_PAYMENT_CREDIT_CARD_NUMBER);
    assertThat(creditCard.getExpirationDate()).isNotNull().isNotEmpty()
        .isEqualTo(DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE);
    assertThat(creditCard.getCvv()).isNotNull().isNotEmpty()
        .isEqualTo(DEFAULT_PAYMENT_CREDIT_CARD_CVV);
    assertThat(creditCard.getValue()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_PAYMENT_VALUE);
  }
}
