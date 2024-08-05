package br.com.fiap.payments.infrastructure.usecase;

import br.com.fiap.payments.application.gateway.PaymentGateway;
import br.com.fiap.payments.application.usecase.GetPaymentsByCustomerCpfUseCase;
import br.com.fiap.payments.domain.entity.Payment;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetPaymentsByCustomerCpfInteractor implements GetPaymentsByCustomerCpfUseCase {

  private final PaymentGateway paymentGateway;

  public GetPaymentsByCustomerCpfInteractor(PaymentGateway paymentGateway) {
    this.paymentGateway = paymentGateway;
  }

  @Override
  public List<Payment> execute(String cpf) {
    return paymentGateway.findByCpf(cpf);
  }
}
