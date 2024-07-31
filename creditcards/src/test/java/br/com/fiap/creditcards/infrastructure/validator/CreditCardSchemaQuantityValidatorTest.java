package br.com.fiap.creditcards.infrastructure.validator;

import static br.com.fiap.creditcards.infrastructure.validator.CreditCardSchemaQuantityValidator.CREDIT_CARD_VALID_MESSAGE;
import static br.com.fiap.creditcards.infrastructure.validator.CreditCardSchemaQuantityValidator.CREDIT_CARD_VALID_QUANTITY;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_CPF;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.createCreditCard;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.fiap.creditcards.application.gateway.CreditCardGateway;
import br.com.fiap.creditcards.domain.exception.ValidatorException;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreditCardSchemaQuantityValidatorTest {

  @Mock
  private CreditCardGateway creditCardGateway;
  @InjectMocks
  private CreditCardSchemaQuantityValidator creditCardSchemaQuantityValidator;

  @Test
  void shouldThrowValidatorExceptionWhenCreditCardNumberRequestedIsGreaterThanTwo() {
    var creditCard1 = createCreditCard();
    var creditCard2 = createCreditCard();
    when(creditCardGateway.findByCpf(DEFAULT_CREDIT_CARD_CPF)).thenReturn(
        List.of(creditCard1, creditCard2));

    assertThatThrownBy(
        () -> creditCardSchemaQuantityValidator.validate(DEFAULT_CREDIT_CARD_CPF))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(CREDIT_CARD_VALID_MESSAGE.formatted(CREDIT_CARD_VALID_QUANTITY));
  }

  @Test
  void shouldValidateCreditCardNumberRequested() {
    var creditCard1 = createCreditCard();
    when(creditCardGateway.findByCpf(DEFAULT_CREDIT_CARD_CPF)).thenReturn(
        List.of(creditCard1));

    assertThatCode(() -> creditCardSchemaQuantityValidator.validate(DEFAULT_CREDIT_CARD_CPF))
        .doesNotThrowAnyException();
  }
}
