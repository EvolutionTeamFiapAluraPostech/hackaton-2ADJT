package br.com.fiap.payments.infrastructure.usecase;

import br.com.fiap.payments.application.gateway.PaymentGateway;
import br.com.fiap.payments.application.usecase.CreatePaymentUseCase;
import br.com.fiap.payments.application.validator.CreditCardExpirationDateValidator;
import br.com.fiap.payments.domain.entity.Payment;
import br.com.fiap.payments.infrastructure.httpclient.creditcard.CreditCardHttpClient;
import br.com.fiap.payments.infrastructure.httpclient.creditcard.dto.CreditCardDto;
import br.com.fiap.payments.infrastructure.validator.CreditCardInteractorCvvValidator;
import br.com.fiap.payments.infrastructure.validator.CreditCardInteractorLimitValidator;
import br.com.fiap.payments.presentation.dto.PaymentInputDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreatePaymentInteractor implements CreatePaymentUseCase {

  private final PaymentGateway paymentGateway;
  private final CreditCardHttpClient creditCardHttpClient;
  private final CreditCardExpirationDateValidator creditCardExpirationDateValidator;
  private final CreditCardInteractorCvvValidator creditCardInteractorCvvValidator;
  private final CreditCardInteractorLimitValidator creditCardInteractorLimitValidator;

  public CreatePaymentInteractor(PaymentGateway paymentGateway,
      CreditCardHttpClient creditCardHttpClient,
      CreditCardExpirationDateValidator creditCardExpirationDateValidator,
      CreditCardInteractorCvvValidator creditCardInteractorCvvValidator,
      CreditCardInteractorLimitValidator creditCardInteractorLimitValidator) {
    this.paymentGateway = paymentGateway;
    this.creditCardHttpClient = creditCardHttpClient;
    this.creditCardExpirationDateValidator = creditCardExpirationDateValidator;
    this.creditCardInteractorCvvValidator = creditCardInteractorCvvValidator;
    this.creditCardInteractorLimitValidator = creditCardInteractorLimitValidator;
  }

  @Transactional
  @Override
  public Payment execute(PaymentInputDto paymentInputDto) {
    var payment = new Payment(paymentInputDto.cpf(), paymentInputDto.numero(),
        paymentInputDto.data_validade(), paymentInputDto.cvv(), paymentInputDto.valor());
    var creditCardDtoResponseEntity = creditCardHttpClient.getCreditCardByNumberAndCpf(
        payment.getNumber(), payment.getCpf());
    if (creditCardDtoResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
      validateCreditCardAttributes(creditCardDtoResponseEntity, payment);
      return paymentGateway.save(payment);
    }
    return null;
  }

  private void validateCreditCardAttributes(
      ResponseEntity<CreditCardDto> creditCardDtoResponseEntity,
      Payment payment) {
    var creditCardDto = creditCardDtoResponseEntity.getBody();
    if (creditCardDto != null) {
      creditCardExpirationDateValidator.validate(creditCardDto.data_validade(),
          payment.getExpirationDate());
      creditCardInteractorCvvValidator.validate(creditCardDto.cvv(), payment.getCvv());
      creditCardInteractorLimitValidator.validate(creditCardDto.limite(), payment.getValue());
    }
  }
}
