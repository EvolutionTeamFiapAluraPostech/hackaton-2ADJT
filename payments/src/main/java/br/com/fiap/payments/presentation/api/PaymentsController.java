package br.com.fiap.payments.presentation.api;

import br.com.fiap.payments.application.usecase.CreatePaymentUseCase;
import br.com.fiap.payments.presentation.dto.PaymentInputDto;
import br.com.fiap.payments.presentation.dto.PaymentOutputDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagamentos")
public class PaymentsController implements PaymentsApi {

  private final CreatePaymentUseCase createPaymentUseCase;

  public PaymentsController(CreatePaymentUseCase createPaymentUseCase) {
    this.createPaymentUseCase = createPaymentUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Override
  public PaymentOutputDto postPayment(PaymentInputDto paymentInputDto) {
    var payment = createPaymentUseCase.execute(paymentInputDto);
    return new PaymentOutputDto(payment.getId());
  }
}
