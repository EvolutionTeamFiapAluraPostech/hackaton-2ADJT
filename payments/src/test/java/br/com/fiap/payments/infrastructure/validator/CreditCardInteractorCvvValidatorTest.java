package br.com.fiap.payments.infrastructure.validator;

import static br.com.fiap.payments.shared.testdata.PaymentsTestData.ALTERNATIVE_PAYMENT_CREDIT_CARD_EXPIRATION_DATE;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.payments.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreditCardInteractorCvvValidatorTest {

  @Spy
  private CreditCardInteractorCvvValidator creditCardInteractorCvvValidator;

  @Test
  void shouldThrowValidatorExceptionWhenPaymentCreditCardExpirationDateIsDifferentOfCreditCardExpirationDate() {
    assertThatThrownBy(() -> creditCardInteractorCvvValidator.validate(
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE,
        ALTERNATIVE_PAYMENT_CREDIT_CARD_EXPIRATION_DATE))
        .isInstanceOf(ValidatorException.class);
  }

  @Test
  void shouldValidateWhenPaymentCreditCardExpirationDateIsEqualOfCreditCardExpirationDate() {
    assertThatCode(() -> creditCardInteractorCvvValidator.validate(
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE))
        .doesNotThrowAnyException();
  }
}
