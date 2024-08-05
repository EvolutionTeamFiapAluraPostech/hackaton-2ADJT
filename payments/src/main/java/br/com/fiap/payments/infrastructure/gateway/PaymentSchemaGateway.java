package br.com.fiap.payments.infrastructure.gateway;

import br.com.fiap.payments.application.gateway.PaymentGateway;
import br.com.fiap.payments.domain.entity.Payment;
import br.com.fiap.payments.infrastructure.repository.PaymentSchemaRepository;
import br.com.fiap.payments.infrastructure.schema.PaymentSchema;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PaymentSchemaGateway implements PaymentGateway {

  private final PaymentSchemaRepository paymentSchemaRepository;

  public PaymentSchemaGateway(PaymentSchemaRepository paymentSchemaRepository) {
    this.paymentSchemaRepository = paymentSchemaRepository;
  }

  @Override
  public Payment save(Payment payment) {
    var paymentSchema = createNewPaymentSchemaFrom(payment);
    var paymentSchemaSaved = paymentSchemaRepository.save(paymentSchema);
    return getPaymentFrom(paymentSchemaSaved);
  }

  @Override
  public List<Payment> findByCpf(String cpf) {
    var payments = paymentSchemaRepository.findByCpf(cpf);
    return payments.stream().map(this::getPaymentFrom).toList();
  }

  private PaymentSchema createNewPaymentSchemaFrom(Payment payment) {
    return PaymentSchema.builder()
        .cpf(payment.getCpf())
        .number(payment.getNumber())
        .expirationDate(payment.getExpirationDate())
        .cvv(payment.getCvv())
        .value(new BigDecimal(payment.getValue()))
        .status(payment.getStatus())
        .build();
  }

  private Payment getPaymentFrom(PaymentSchema paymentSchemaSaved) {
    return new Payment(paymentSchemaSaved.getId().toString(), paymentSchemaSaved.getCpf(),
        paymentSchemaSaved.getNumber(), paymentSchemaSaved.getExpirationDate(),
        paymentSchemaSaved.getCvv(), paymentSchemaSaved.getValue().toString(),
        paymentSchemaSaved.getStatus());
  }
}
