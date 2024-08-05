package br.com.fiap.payments.application.gateway;

import br.com.fiap.payments.domain.entity.Payment;
import java.util.List;

public interface PaymentGateway {

  Payment save(Payment payment);

  List<Payment> findByCpf(String cpf);
}
