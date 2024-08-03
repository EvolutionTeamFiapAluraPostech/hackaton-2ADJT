package br.com.fiap.payments.application.validator;

public interface CreditCardLimitValidator {

  void validate(String limit, String value);
}
