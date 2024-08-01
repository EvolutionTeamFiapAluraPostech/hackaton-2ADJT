package br.com.fiap.creditcards.infrastructure.usecase;

import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_CPF;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_ID;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_NUMBER;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.createCreditCard;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.createCreditCardSchemaFrom;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import br.com.fiap.creditcards.application.gateway.CreditCardGateway;
import br.com.fiap.creditcards.domain.exception.NoResultException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GetCreditCardByNumberAndCustomerCpfInteractorTest {

  @Mock
  private CreditCardGateway creditCardGateway;
  @InjectMocks
  private GetCreditCardByNumberAndCustomerCpfInteractor getCreditCardByNumberAndCustomerCpfInteractor;

  @Test
  void shouldThrowNoResultExceptionWhenCreditCardWasNotFoundByNumberAndCpf() {
    when(creditCardGateway.findByNumberAndCpfRequired(DEFAULT_CREDIT_CARD_NUMBER,
        DEFAULT_CREDIT_CARD_CPF)).thenThrow(NoResultException.class);

    assertThatThrownBy(
        () -> getCreditCardByNumberAndCustomerCpfInteractor.execute(DEFAULT_CREDIT_CARD_NUMBER,
            DEFAULT_CREDIT_CARD_CPF))
        .isInstanceOf(NoResultException.class);
  }

  @Test
  void shouldFindCreditCardByNumberAndCpf() {
    var creditCard = createCreditCard();
    var creditCardSchema = createCreditCardSchemaFrom(creditCard);
    when(creditCardGateway.findByNumberAndCpfRequired(DEFAULT_CREDIT_CARD_NUMBER,
        DEFAULT_CREDIT_CARD_CPF)).thenReturn(creditCard);

    var creditCardFound = getCreditCardByNumberAndCustomerCpfInteractor.execute(
        DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_CPF);

    assertThat(creditCardFound).isNotNull();
    assertThat(creditCardFound.getId()).isNotNull().isEqualTo(DEFAULT_CREDIT_CARD_ID);
    assertThat(creditCardFound).usingRecursiveComparison().isEqualTo(creditCard);
  }
}