package br.com.fiap.payments.application.validator;

public interface CreditCardCvvValidator {

  void validate(String creditCardCvv, String paymentCreditCardCvv);
}
