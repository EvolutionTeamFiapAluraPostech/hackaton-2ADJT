package br.com.fiap.payments.application.validator;

public interface CreditCardExistsValidator {

  void validate(String number, String cpf);
}
