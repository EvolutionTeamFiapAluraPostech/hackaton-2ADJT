package br.com.fiap.payments.infrastructure.validator;

import static br.com.fiap.payments.shared.testdata.PaymentsTestData.ALTERNATIVE_PAYMENT_CREDIT_CARD_NUMBER;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CPF;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CREDIT_CARD_CVV;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_VALUE;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import br.com.fiap.payments.domain.exception.NoResultException;
import br.com.fiap.payments.infrastructure.httpclient.creditcard.CreditCardHttpClient;
import br.com.fiap.payments.infrastructure.httpclient.creditcard.dto.CreditCardDto;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CreditCardExistsInCreditCardsMicroserviceValidatorTest {

  @Mock
  private CreditCardHttpClient creditCardHttpClient;
  @InjectMocks
  private CreditCardExistsInCreditCardsMicroserviceValidator creditCardExistsInCreditCardsMicroserviceValidator;

  @Test
  void shouldThrowExceptionWhenCreditCardDoesNotExistInCreditCardsMicroservice() {
    doThrow(NoResultException.class).when(creditCardHttpClient)
        .getCreditCardByNumberAndCpf(ALTERNATIVE_PAYMENT_CREDIT_CARD_NUMBER, DEFAULT_PAYMENT_CPF);

    assertThatThrownBy(() -> creditCardExistsInCreditCardsMicroserviceValidator.validate(
        ALTERNATIVE_PAYMENT_CREDIT_CARD_NUMBER, DEFAULT_PAYMENT_CPF))
        .isInstanceOf(NoResultException.class);
  }

  @Test
  void shouldValidateWhenCreditCardExistsInCreditCardsMicroservice() {
    var creditCard = new CreditCardDto(UUID.randomUUID().toString(), DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_VALUE, ALTERNATIVE_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV);
    var creditCardDtoResponseEntity = ResponseEntity.ok(creditCard);
    when(creditCardHttpClient.getCreditCardByNumberAndCpf(ALTERNATIVE_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CPF)).thenReturn(creditCardDtoResponseEntity);

    assertThatCode(() -> creditCardExistsInCreditCardsMicroserviceValidator.validate(
        ALTERNATIVE_PAYMENT_CREDIT_CARD_NUMBER, DEFAULT_PAYMENT_CPF)).doesNotThrowAnyException();
  }
}
