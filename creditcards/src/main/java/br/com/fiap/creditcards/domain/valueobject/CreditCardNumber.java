package br.com.fiap.creditcards.domain.valueobject;

import br.com.fiap.creditcards.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class CreditCardNumber {

  public static final Integer NUMBER_LENGTH = 16;
  public static final String NUMBER_FIELD = "numero";
  public static final String NUMBER_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE = "O número do cartão de crédito não pode ser %s.";
  public static final String NUMBER_VALUE_LENGTH_INVALID_MESSAGE = "O número do cartão de crédito deve possuir %s caracteres. Você informou %s.";
  private final String number;

  public CreditCardNumber(String number) {
    validateNullOrEmpty(number);
    validateLength(number);
    this.number = number;
  }

  private void validateLength(String number) {
    if (number.length() != NUMBER_LENGTH) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), NUMBER_FIELD,
          NUMBER_VALUE_LENGTH_INVALID_MESSAGE.formatted(NUMBER_LENGTH, number)));
    }
  }

  private void validateNullOrEmpty(String number) {
    if (number == null || number.isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), NUMBER_FIELD,
          NUMBER_VALUE_CANNOT_BE_NULL_OR_EMPTY_MESSAGE.formatted(number)));
    }
  }

  public String getNumber() {
    return number;
  }
}
