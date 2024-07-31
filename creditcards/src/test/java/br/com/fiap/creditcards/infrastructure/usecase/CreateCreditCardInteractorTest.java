package br.com.fiap.creditcards.infrastructure.usecase;

import static br.com.fiap.creditcards.domain.valueobject.Cpf.CPF_FIELD;
import static br.com.fiap.creditcards.domain.valueobject.CreditCardNumber.NUMBER_FIELD;
import static br.com.fiap.creditcards.infrastructure.validator.CreditCardSchemaNumberAlreadyExistsValidator.CREDIT_CARD_ALREADY_EXISTS;
import static br.com.fiap.creditcards.infrastructure.validator.CreditCardSchemaQuantityValidator.CREDIT_CARD_VALID_MESSAGE;
import static br.com.fiap.creditcards.infrastructure.validator.CreditCardSchemaQuantityValidator.CREDIT_CARD_VALID_QUANTITY;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_CPF;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_NUMBER;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.createNewCreditCardInputDto;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import br.com.fiap.creditcards.application.gateway.CreditCardGateway;
import br.com.fiap.creditcards.application.validator.CreditCardNumberAlreadyExistsValidator;
import br.com.fiap.creditcards.application.validator.CreditCardQuantityValidator;
import br.com.fiap.creditcards.application.validator.CustomerExistsValidator;
import br.com.fiap.creditcards.domain.entity.CreditCard;
import br.com.fiap.creditcards.domain.exception.DuplicatedException;
import br.com.fiap.creditcards.domain.exception.NoResultException;
import br.com.fiap.creditcards.domain.exception.ValidatorException;
import br.com.fiap.creditcards.shared.testdata.CreditCardTestData;
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
  @Mock
  private CreditCardQuantityValidator creditCardQuantityValidator;
  @Mock
  private CustomerExistsValidator customerExistsValidator;
  @InjectMocks
  private CreateCreditCardInteractor createCreditCardInteractor;

  @Test
  void shouldThrowNoResultExceptionWhenCustomerDoesNotExistInCustomersMicroservice() {
    doThrow(NoResultException.class).when(customerExistsValidator)
        .validate(DEFAULT_CREDIT_CARD_CPF);

    assertThatThrownBy(() -> customerExistsValidator.validate(DEFAULT_CREDIT_CARD_CPF))
        .isInstanceOf(NoResultException.class);

    verify(creditCardNumberAlreadyExistsValidator, never()).validate(any(String.class));
    verify(creditCardQuantityValidator, never()).validate(any(String.class));
    verify(creditCardGateway, never()).save(any(CreditCard.class));
  }

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

    verify(creditCardQuantityValidator, never()).validate(any(String.class));
    verify(creditCardGateway, never()).save(any(CreditCard.class));
  }

  @Test
  void shouldThrowValidatorExceptionWhenCustomerAlreadyHasTwoCreditCardsAndWantToCreateOneMore() {
    var creditCardInputDto = CreditCardTestData.createNewCreditCardInputDto();
    doThrow(new ValidatorException(new FieldError(this.getClass().getSimpleName(), CPF_FIELD,
        CREDIT_CARD_VALID_MESSAGE.formatted(CREDIT_CARD_VALID_QUANTITY)))).when(
        creditCardQuantityValidator).validate(creditCardInputDto.cpf());

    assertThatThrownBy(() -> createCreditCardInteractor.execute(creditCardInputDto))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(CREDIT_CARD_VALID_MESSAGE.formatted(CREDIT_CARD_VALID_QUANTITY));

    verify(creditCardGateway, never()).save(any(CreditCard.class));
  }

  @Test
  void shouldCreateCreditCardWhenAllAttributesAreCorrect() {
    var creditCardInputDto = CreditCardTestData.createNewCreditCardInputDto();
    doNothing().when(creditCardNumberAlreadyExistsValidator).validate(creditCardInputDto.numero());
    doNothing().when(creditCardQuantityValidator).validate(creditCardInputDto.cpf());

    assertThatCode(
        () -> createCreditCardInteractor.execute(creditCardInputDto)).doesNotThrowAnyException();
  }
}
