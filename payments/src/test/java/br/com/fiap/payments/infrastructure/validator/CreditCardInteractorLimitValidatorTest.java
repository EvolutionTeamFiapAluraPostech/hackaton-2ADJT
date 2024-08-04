package br.com.fiap.payments.infrastructure.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.payments.domain.exception.LimitInvalidException;
import br.com.fiap.payments.domain.valueobject.AccountValue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreditCardInteractorLimitValidatorTest {

  @Spy
  private CreditCardInteractorLimitValidator creditCardInteractorLimitValidator;

  @Test
  void shouldThrowValidatorExceptionWhenPaymentValueIsGreaterThanLimitValue() {
    var creditCardLimit = "1000";
    var paymentValue = "2000";
    assertThatThrownBy(
        () -> creditCardInteractorLimitValidator.validate(creditCardLimit, paymentValue))
        .isInstanceOf(LimitInvalidException.class)
        .hasMessage(AccountValue.PAYMENT_VALUE_CANNOT_BE_GREATER_THAN_LIMIT_VALUE_MESSAGE.formatted(
            creditCardLimit, paymentValue));
  }

  @Test
  void shouldValidateWhenPaymentValueIsEqualOrLessThanLimitValue() {
    var creditCardLimit = "2000.01";
    var paymentValue = "2000.00";
    assertThatCode(
        () -> creditCardInteractorLimitValidator.validate(creditCardLimit,
            paymentValue)).doesNotThrowAnyException();
  }
}