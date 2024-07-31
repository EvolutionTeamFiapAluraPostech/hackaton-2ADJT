package br.com.fiap.creditcards.infrastructure.usecase;

import static br.com.fiap.creditcards.domain.valueobject.CreditCardNumber.NUMBER_FIELD;
import static br.com.fiap.creditcards.infrastructure.validator.CreditCardSchemaNumberAlreadyExistsValidator.CREDIT_CARD_ALREADY_EXISTS;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_NUMBER;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.createNewCreditCardInputDto;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import br.com.fiap.creditcards.application.gateway.CreditCardGateway;
import br.com.fiap.creditcards.application.validator.CreditCardNumberAlreadyExistsValidator;
import br.com.fiap.creditcards.domain.entity.CreditCard;
import br.com.fiap.creditcards.domain.exception.DuplicatedException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.FieldError;

@ExtendWith(MockitoExtension.class)
class CreateCreditCardInteractorTest {

  @Mock
  private CreditCardGateway creditCardGateway;
  @Mock
  private CreditCardNumberAlreadyExistsValidator creditCardNumberAlreadyExistsValidator;
  @InjectMocks
  private CreateCreditCardInteractor createCreditCardInteractor;

  @Test
  void shouldThrowDuplicatedExceptionWhenCreditCardNumberAlreadyExists() {
    var creditCard = createNewCreditCardInputDto();
    doThrow(new DuplicatedException(new FieldError(this.getClass().getSimpleName(), NUMBER_FIELD,
        CREDIT_CARD_ALREADY_EXISTS.formatted(DEFAULT_CREDIT_CARD_NUMBER)))).when(
        creditCardNumberAlreadyExistsValidator).validate(
        DEFAULT_CREDIT_CARD_NUMBER);

    assertThatThrownBy(() -> createCreditCardInteractor.execute(creditCard))
        .isInstanceOf(DuplicatedException.class)
        .hasMessage(CREDIT_CARD_ALREADY_EXISTS.formatted(DEFAULT_CREDIT_CARD_NUMBER));

    verify(creditCardGateway, never()).save(any(CreditCard.class));
  }

  @Test
  void shouldThrowValidatorExceptionWhenCustomerAlreadyHasTwoCreditCardsAndWantToCreateOneMore() {
    fail(
        "shouldThrowValidatorExceptionWhenCustomerAlreadyHasTwoCreditCardsAndWantToCreateOneMore");
  }

  @Test
  void shouldThrowNoResultExceptionWhenCpfDoesNotExistInCustomerMicroservice() {
    fail("shouldThrowNoResultExceptionWhenCpfDoesNotExistInCustomerMicroservice");
  }

  @Test
  void shouldCreateCreditCardWhenAllAttributesAreCorrect() {
    fail("shouldCreateCreditCardWhenAllAttributesAreCorrect");
  }
}
