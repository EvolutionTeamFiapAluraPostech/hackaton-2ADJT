package br.com.fiap.payments.application.validator;

public interface CreditCardExpirationDateValidator {

  void validate(String creditCardExpirationDate, String paymentCreditCardExpirationDate);
}
