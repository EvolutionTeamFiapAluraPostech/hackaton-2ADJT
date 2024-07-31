package br.com.fiap.creditcards.infrastructure.validator;

import static br.com.fiap.creditcards.infrastructure.validator.CreditCardSchemaNumberAlreadyExistsValidator.CREDIT_CARD_ALREADY_EXISTS;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_NUMBER;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.createCreditCard;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.fiap.creditcards.application.gateway.CreditCardGateway;
import br.com.fiap.creditcards.domain.exception.ValidatorException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreditCardSchemaNumberAlreadyExistsValidatorTest {

  @Mock
  private CreditCardGateway creditCardGateway;
  @InjectMocks
  private CreditCardSchemaNumberAlreadyExistsValidator creditCardSchemaNumberAlreadyExistsValidator;

  @Test
  void shouldThrowValidatorExceptionWhenCreditCardNumberAlreadyExists() {
    var creditCard = createCreditCard();
    when(creditCardGateway.findByNumber(DEFAULT_CREDIT_CARD_NUMBER)).thenReturn(creditCard);

    assertThatThrownBy(
        () -> creditCardSchemaNumberAlreadyExistsValidator.validate(DEFAULT_CREDIT_CARD_NUMBER))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(CREDIT_CARD_ALREADY_EXISTS.formatted(DEFAULT_CREDIT_CARD_NUMBER));
  }

  @Test
  void shouldValidateWhenCreditCardNumberDoesNotExist() {
    when(creditCardGateway.findByNumber(DEFAULT_CREDIT_CARD_NUMBER)).thenReturn(null);

    assertThatCode(() -> creditCardSchemaNumberAlreadyExistsValidator.validate(
        DEFAULT_CREDIT_CARD_NUMBER)).doesNotThrowAnyException();
  }
}
