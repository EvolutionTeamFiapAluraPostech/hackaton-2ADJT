package br.com.fiap.creditcards.domain.entity;

import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_CPF;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_CVV;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_EXPIRATION_DATE;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_ID;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_LIMIT;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_NUMBER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.creditcards.domain.exception.ValidatorException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CreditCardTest {

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"A", "1", "11111111111", "1234567890", "123456789012"})
  void shouldThrowValidatorExceptionWhenCpfIsInvalid(String cpf) {
    assertThatThrownBy(() -> new CreditCard(cpf, DEFAULT_CREDIT_CARD_LIMIT,
        DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_EXPIRATION_DATE,
        DEFAULT_CREDIT_CARD_CVV)).isInstanceOf(ValidatorException.class);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"-1", "A"})
  void shouldThrowValidatorExceptionWhenLimitIsInvalid(String limit) {
    assertThatThrownBy(() -> new CreditCard(DEFAULT_CREDIT_CARD_CPF, limit,
        DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_EXPIRATION_DATE,
        DEFAULT_CREDIT_CARD_CVV)).isInstanceOf(ValidatorException.class);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1", "12345678901234567"})
  void shouldThrowValidatorExceptionWhenCreditCardNumberIsInvalid(String number) {
    assertThatThrownBy(() -> new CreditCard(DEFAULT_CREDIT_CARD_CPF,
        DEFAULT_CREDIT_CARD_LIMIT, number, DEFAULT_CREDIT_CARD_EXPIRATION_DATE,
        DEFAULT_CREDIT_CARD_CVV)).isInstanceOf(ValidatorException.class);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1", "12/2024"})
  void shouldThrowValidatorExceptionWhenExpirationDateIsInvalid(String expirationDate) {
    assertThatThrownBy(() -> new CreditCard(DEFAULT_CREDIT_CARD_CPF,
        DEFAULT_CREDIT_CARD_LIMIT, DEFAULT_CREDIT_CARD_NUMBER, expirationDate,
        DEFAULT_CREDIT_CARD_CVV)).isInstanceOf(ValidatorException.class);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"1", "1234"})
  void shouldThrowValidatorExceptionWhenCvvIsInvalid(String cvv) {
    assertThatThrownBy(() -> new CreditCard(DEFAULT_CREDIT_CARD_CPF,
        DEFAULT_CREDIT_CARD_LIMIT, DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_EXPIRATION_DATE,
        cvv)).isInstanceOf(ValidatorException.class);
  }

  @Test
  void shouldCreateCreditCardWhenAllAttributesAreCorrect() {
    var creditCard = new CreditCard(DEFAULT_CREDIT_CARD_ID, DEFAULT_CREDIT_CARD_CPF,
        DEFAULT_CREDIT_CARD_LIMIT, DEFAULT_CREDIT_CARD_NUMBER,
        DEFAULT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_CREDIT_CARD_CVV);

    assertThat(creditCard).isNotNull();
    assertThat(creditCard.getId()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_CREDIT_CARD_ID);
    assertThat(creditCard.getCpf()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_CREDIT_CARD_CPF);
    assertThat(creditCard.getLimit()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_CREDIT_CARD_LIMIT);
    assertThat(creditCard.getNumber()).isNotNull().isNotEmpty()
        .isEqualTo(DEFAULT_CREDIT_CARD_NUMBER);
    assertThat(creditCard.getExpirationDate()).isNotNull().isNotEmpty()
        .isEqualTo(DEFAULT_CREDIT_CARD_EXPIRATION_DATE);
    assertThat(creditCard.getCvv()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_CREDIT_CARD_CVV);
  }

  @Test
  void shouldCalculateNewLimit() {
    var creditCard = new CreditCard(DEFAULT_CREDIT_CARD_ID, DEFAULT_CREDIT_CARD_CPF,
        DEFAULT_CREDIT_CARD_LIMIT, DEFAULT_CREDIT_CARD_NUMBER,
        DEFAULT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_CREDIT_CARD_CVV);

    var newLimit = creditCard.getNewLimit(new BigDecimal("350.00"));

    assertThat(creditCard).isNotNull();
    assertThat(newLimit).isEqualTo(new BigDecimal("650.00"));
  }
}
