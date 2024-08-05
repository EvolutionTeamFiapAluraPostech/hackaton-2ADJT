package br.com.fiap.payments.application.usecase;

import br.com.fiap.payments.domain.entity.Payment;
import java.util.List;

public interface GetPaymentsByCustomerCpfUseCase {

  List<Payment> execute(String cpf);
}
