package br.com.fiap.payments.infrastructure.usecase;

import br.com.fiap.payments.application.gateway.PaymentGateway;
import br.com.fiap.payments.application.usecase.CreatePaymentUseCase;
import br.com.fiap.payments.domain.entity.Payment;
import br.com.fiap.payments.presentation.dto.PaymentInputDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreatePaymentInteractor implements CreatePaymentUseCase {

  private final PaymentGateway paymentGateway;

  public CreatePaymentInteractor(PaymentGateway paymentGateway) {
    this.paymentGateway = paymentGateway;
  }

  @Transactional
  @Override
  public Payment execute(PaymentInputDto paymentInputDto) {
    var payment = new Payment(paymentInputDto.cpf(), paymentInputDto.numero(),
        paymentInputDto.data_validade(), paymentInputDto.cvv(), paymentInputDto.valor());
    return paymentGateway.save(payment);
  }
}
