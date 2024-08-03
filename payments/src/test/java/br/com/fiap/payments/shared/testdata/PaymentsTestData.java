package br.com.fiap.payments.shared.testdata;

import br.com.fiap.payments.domain.entity.Payment;
import br.com.fiap.payments.infrastructure.schema.PaymentSchema;
import br.com.fiap.payments.presentation.dto.PaymentInputDto;
import java.util.UUID;

public final class PaymentsTestData {

  public static final String DEFAULT_PAYMENT_ID = "7bc4462a-e7b1-4faa-a7ee-9361df10b341";
  public static final UUID DEFAULT_PAYMENT_UUID = UUID.fromString(DEFAULT_PAYMENT_ID);
  public static final String DEFAULT_PAYMENT_CPF = "84527263846";
  public static final String DEFAULT_PAYMENT_CREDIT_CARD_NUMBER = "1234567890123456";
  public static final String DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE = "12/24";
  public static final String DEFAULT_PAYMENT_CREDIT_CARD_CVV = "123";
  public static final String DEFAULT_PAYMENT_VALUE = "1000.00";
  public static final String ALTERNATIVE_PAYMENT_CPF = "20416133606";
  public static final String ALTERNATIVE_PAYMENT_CREDIT_CARD_NUMBER = "9876543210987654";
  public static final String ALTERNATIVE_PAYMENT_CREDIT_CARD_EXPIRATION_DATE = "06/23";
  public static final String ALTERNATIVE_PAYMENT_CREDIT_CARD_CVV = "321";
  public static final String ALTERNATIVE_PAYMENT_VALUE = "2000.00";

  private PaymentsTestData() {
  }

  public static Payment createNewPayment() {
    return new Payment(DEFAULT_PAYMENT_CPF, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE);
  }

  public static Payment createPayment() {
    return new Payment(DEFAULT_PAYMENT_ID, DEFAULT_PAYMENT_CPF, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE);
  }

  public static PaymentSchema createPaymentSchema(Payment payment) {
    return PaymentSchema.builder()
        .id(DEFAULT_PAYMENT_UUID)
        .cpf(DEFAULT_PAYMENT_CPF)
        .number(DEFAULT_PAYMENT_CREDIT_CARD_NUMBER)
        .expirationDate(DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE)
        .cvv(DEFAULT_PAYMENT_CREDIT_CARD_CVV)
        .value(DEFAULT_PAYMENT_VALUE)
        .build();
  }

  public static PaymentInputDto createNewPaymentInputDto() {
    return new PaymentInputDto(DEFAULT_PAYMENT_CPF, DEFAULT_PAYMENT_CREDIT_CARD_NUMBER,
        DEFAULT_PAYMENT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_PAYMENT_CREDIT_CARD_CVV,
        DEFAULT_PAYMENT_VALUE);
  }
}
