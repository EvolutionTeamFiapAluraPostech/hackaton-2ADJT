package br.com.fiap.creditcards.infrastructure.usecase;

import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_CPF;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_CVV;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_EXPIRATION_DATE;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_ID;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_NUMBER;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.createCreditCard;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import br.com.fiap.creditcards.application.gateway.CreditCardGateway;
import br.com.fiap.creditcards.domain.entity.CreditCard;
import br.com.fiap.creditcards.presentation.dto.CreditCardPaymentValueDto;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PatchCreditCardLimitByNumberAndCustomerCpfInteractorTest {

  @Mock
  private CreditCardGateway creditCardGateway;
  @InjectMocks
  private PatchCreditCardLimitByNumberAndCustomerCpfInteractor patchCreditCardLimitByNumberAndCustomerCpfInteractor;

  @Test
  void shouldUpdateCreditCardLimit() {
    var creditCard = createCreditCard();
    when(creditCardGateway.findByNumberAndCpfRequired(DEFAULT_CREDIT_CARD_NUMBER,
        DEFAULT_CREDIT_CARD_CPF)).thenReturn(creditCard);

    var paymentValue = new BigDecimal("250.00");
    var creditCardPaymentValueDto = new CreditCardPaymentValueDto(paymentValue);
    var newLimit = creditCard.getNewLimit(paymentValue);
    var creditCardSaved = new CreditCard(DEFAULT_CREDIT_CARD_ID, DEFAULT_CREDIT_CARD_CPF,
        newLimit.toString(), DEFAULT_CREDIT_CARD_NUMBER, DEFAULT_CREDIT_CARD_EXPIRATION_DATE,
        DEFAULT_CREDIT_CARD_CVV);
    when(creditCardGateway.updateLimit(creditCard, newLimit)).thenReturn(creditCardSaved);

    patchCreditCardLimitByNumberAndCustomerCpfInteractor.execute(creditCard.getNumber(),
        creditCard.getCpf(), creditCardPaymentValueDto);

    assertThat(new BigDecimal(creditCardSaved.getLimit())).isEqualTo(newLimit);
  }
}
