package br.com.fiap.payments.application.gateway;

import br.com.fiap.payments.domain.entity.Payment;

public interface PaymentGateway {

  Payment save(Payment payment);
}
