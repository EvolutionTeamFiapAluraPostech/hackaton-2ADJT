package br.com.fiap.payments.application.usecase;

import br.com.fiap.payments.domain.entity.Payment;
import br.com.fiap.payments.presentation.dto.PaymentInputDto;

public interface CreatePaymentUseCase {

  Payment execute(PaymentInputDto paymentInputDto);
}
