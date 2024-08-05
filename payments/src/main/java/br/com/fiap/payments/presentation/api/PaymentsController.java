package br.com.fiap.payments.presentation.api;

import br.com.fiap.payments.application.usecase.CreatePaymentUseCase;
import br.com.fiap.payments.application.usecase.GetPaymentsByCustomerCpfUseCase;
import br.com.fiap.payments.domain.entity.Payment;
import br.com.fiap.payments.presentation.dto.PaymentDto;
import br.com.fiap.payments.presentation.dto.PaymentInputDto;
import br.com.fiap.payments.presentation.dto.PaymentOutputDto;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagamentos")
public class PaymentsController implements PaymentsApi {

  private final CreatePaymentUseCase createPaymentUseCase;
  private final GetPaymentsByCustomerCpfUseCase getPaymentsByCustomerCpfUseCase;

  public PaymentsController(CreatePaymentUseCase createPaymentUseCase,
      GetPaymentsByCustomerCpfUseCase getPaymentsByCustomerCpfUseCase) {
    this.createPaymentUseCase = createPaymentUseCase;
    this.getPaymentsByCustomerCpfUseCase = getPaymentsByCustomerCpfUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Override
  public PaymentOutputDto postPayment(@RequestBody PaymentInputDto paymentInputDto) {
    var payment = createPaymentUseCase.execute(paymentInputDto);
    return convertPaymentIdToPaymentOutputDto(payment);
  }

  @NotNull
  private PaymentOutputDto convertPaymentIdToPaymentOutputDto(Payment payment) {
    return new PaymentOutputDto(payment.getId());
  }

  @GetMapping("/cliente/{cpf}")
  @Override
  public List<PaymentDto> getPaymentsByCustomerCpf(@PathVariable String cpf) {
    var payments = getPaymentsByCustomerCpfUseCase.execute(cpf);
    return convertPaymentsListToPaymentDtoList(payments);
  }

  @NotNull
  private List<PaymentDto> convertPaymentsListToPaymentDtoList(List<Payment> payments) {
    return payments.stream().map(PaymentDto::new).toList();
  }
}
