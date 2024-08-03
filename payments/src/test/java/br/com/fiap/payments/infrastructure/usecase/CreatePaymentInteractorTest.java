package br.com.fiap.payments.infrastructure.usecase;

import static br.com.fiap.payments.shared.testdata.PaymentsTestData.ALTERNATIVE_PAYMENT_CREDIT_CARD_CVV;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.ALTERNATIVE_PAYMENT_CREDIT_CARD_EXPIRATION_DATE;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.ALTERNATIVE_PAYMENT_VALUE;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CPF;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CREDIT_CARD_CVV;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_CREDIT_CARD_NUMBER;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.DEFAULT_PAYMENT_VALUE;
import static br.com.fiap.payments.shared.testdata.PaymentsTestData.createNewPaymentInputDto;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.fiap.payments.application.gateway.PaymentGateway;
import br.com.fiap.payments.domain.entity.Payment;
import br.com.fiap.payments.domain.exception.NoResultException;
import br.com.fiap.payments.domain.exception.ValidatorException;
import br.com.fiap.payments.infrastructure.httpclient.creditcard.CreditCardHttpClient;
import br.com.fiap.payments.infrastructure.httpclient.creditcard.dto.CreditCardDto;
import br.com.fiap.payments.infrastructure.validator.CreditCardInteractorCvvValidator;
import br.com.fiap.payments.infrastructure.validator.CreditCardInteractorExpirationDateValidator;
import br.com.fiap.payments.infrastructure.validator.CreditCardInteractorLimitValidator;
import br.com.fiap.payments.presentation.dto.PaymentInputDto;
import br.com.fiap.payments.shared.testdata.PaymentsTestData;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class CreatePaymentInteractorTest {

  @Mock
  private PaymentGateway paymentGateway;
  @Mock
  private CreditCardHttpClient creditCardHttpClient;
  @Mock
  private CreditCardInteractorExpirationDateValidator creditCardExpirationDateValidator;
  @Mock
  private CreditCardInteractorCvvValidator creditCardInteractorCvvValidator;
  @Mock
  private CreditCardInteractorLimitValidator creditCardInteractorLimitValidator;
  @InjectMocks
  private CreatePaymentInteractor createPaymentInteractor;

  @Test
  void shouldThrowNoResultExceptionWhenCustomerCpfAndCreditCardNumberDoesNotExistInCreditCardMicroservice() {
    var paymentInputDto = createNewPaymentInputDto();
    doThrow(NoResultException.class).when(creditCardHttpClient)
        .getCreditCardByNumberAndCpf(DEFAULT_PAYMENT_CREDIT_CARD_NUMBER, DEFAULT_PAYMENT_CPF);

    assertThatThrownBy(() -> createPaymentInteractor.execute(paymentInputDto))
        .isInstanceOf(NoResultException.class);
    verify(paymentGateway, never()).save(any(Payment.class));
  }

  @Test
  void shouldThrowValidatorExceptionWhenCreditCardExpirationDateIsIncorrect() {
    var paymentInputDto = new PaymentInputDto(DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        ALTERNATIVE_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE);
    var creditCard = new CreditCardDto(UUID.randomUUID().toString(), DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_VALUE, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV);
    var creditCardDtoResponseEntity = ResponseEntity.ok(creditCard);
    when(creditCardHttpClient.getCreditCardByNumberAndCpf(paymentInputDto.numero(),
        paymentInputDto.cpf())).thenReturn(creditCardDtoResponseEntity);
    doThrow(ValidatorException.class).when(creditCardExpirationDateValidator)
        .validate(DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE,
            ALTERNATIVE_PAYMENT_CREDIT_CARD_EXPIRATION_DATE);

    assertThatThrownBy(() -> createPaymentInteractor.execute(paymentInputDto))
        .isInstanceOf(ValidatorException.class);
    verify(paymentGateway, never()).save(any(Payment.class));
  }

  @Test
  void shouldThrowValidatorExceptionWhenCreditCardCvvIsIncorrect() {
    var paymentInputDto = new PaymentInputDto(DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        ALTERNATIVE_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, ALTERNATIVE_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE);
    var creditCard = new CreditCardDto(UUID.randomUUID().toString(), DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_VALUE, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV);
    var creditCardDtoResponseEntity = ResponseEntity.ok(creditCard);
    when(creditCardHttpClient.getCreditCardByNumberAndCpf(paymentInputDto.numero(),
        paymentInputDto.cpf())).thenReturn(creditCardDtoResponseEntity);
    doThrow(ValidatorException.class).when(creditCardInteractorCvvValidator)
        .validate(DEFAULT_PAYMENT_CREDIT_CARD_CVV, ALTERNATIVE_PAYMENT_CREDIT_CARD_CVV);

    assertThatThrownBy(() -> createPaymentInteractor.execute(paymentInputDto))
        .isInstanceOf(ValidatorException.class);
    verify(paymentGateway, never()).save(any(Payment.class));
  }

  @Test
  void shouldThrowPaymentRequiredWhenWhenCreditCardLimitIsNotEnough() {
    var paymentInputDto = new PaymentInputDto(DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        ALTERNATIVE_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, ALTERNATIVE_PAYMENT_CREDIT_CARD_CVV,
        ALTERNATIVE_PAYMENT_VALUE);
    var creditCard = new CreditCardDto(UUID.randomUUID().toString(), DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_VALUE, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV);
    var creditCardDtoResponseEntity = ResponseEntity.ok(creditCard);
    when(creditCardHttpClient.getCreditCardByNumberAndCpf(paymentInputDto.numero(),
        paymentInputDto.cpf())).thenReturn(creditCardDtoResponseEntity);
    doThrow(ValidatorException.class).when(creditCardInteractorLimitValidator)
        .validate(DEFAULT_PAYMENT_VALUE, ALTERNATIVE_PAYMENT_VALUE);

    assertThatThrownBy(() -> createPaymentInteractor.execute(paymentInputDto))
        .isInstanceOf(ValidatorException.class);
    verify(paymentGateway, never()).save(any(Payment.class));
  }

  @Test
  void shouldCreatePaymentWhenAllPaymentsAttributesAreOk() {
    var paymentInputDto = new PaymentInputDto(DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        ALTERNATIVE_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, ALTERNATIVE_PAYMENT_CREDIT_CARD_CVV,
        ALTERNATIVE_PAYMENT_VALUE);
    var creditCard = new CreditCardDto(UUID.randomUUID().toString(), DEFAULT_PAYMENT_CPF,
        DEFAULT_PAYMENT_VALUE, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV);
    var creditCardDtoResponseEntity = ResponseEntity.ok(creditCard);
    when(creditCardHttpClient.getCreditCardByNumberAndCpf(paymentInputDto.numero(),
        paymentInputDto.cpf())).thenReturn(creditCardDtoResponseEntity);
    var paymentWithId = PaymentsTestData.createPayment();
    when(paymentGateway.save(Mockito.any(Payment.class))).thenReturn(paymentWithId);

    var paymentSaved = createPaymentInteractor.execute(paymentInputDto);

    assertThat(paymentSaved).isNotNull();
    assertThat(paymentSaved).usingRecursiveComparison().isEqualTo(paymentWithId);
  }
}
