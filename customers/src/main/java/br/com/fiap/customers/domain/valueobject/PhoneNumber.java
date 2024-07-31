package br.com.fiap.customers.domain.valueobject;

import br.com.fiap.customers.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class PhoneNumber {

  public static final String PHONE_NUMBER_FIELD = "telefone";
  public static final String PHONE_NUMBER_NOT_NULL_MESSAGE = "O número de telefone não pode ser nulo.";
  public static final String PHONE_NUMBER_NOT_EMPTY_MESSAGE = "O número de telefone não pode ser vazio.";
  public static final Integer PHONE_NUMBER_MIN_LENGTH = 11;
  public static final String PHONE_NUMBER_MIN_LENGTH_MESSAGE = "O número de telefone deve possuir no mínimo %s characteres. Você informou %s.";
  public static final Integer PHONE_NUMBER_MAX_LENGTH = 50;
  public static final String PHONE_NUMBER_MAX_LENGTH_MESSAGE = "O número de telefone deve possuir no máximo %s characteres. Você informou %s.";
  private final String phoneNumberValue;

  public PhoneNumber(String phoneNumberValue) {
    validateNullPhoneNumber(phoneNumberValue);
    validateEmptyPhoneNumber(phoneNumberValue);
    validateMinLengthPhoneNumber(phoneNumberValue);
    validateMaxLengthPhoneNumber(phoneNumberValue);
    this.phoneNumberValue = phoneNumberValue;
  }

  private void validateMaxLengthPhoneNumber(String phoneNumberValue) {
    var phoneNumberLength = phoneNumberValue.trim().length();
    if (phoneNumberLength > PHONE_NUMBER_MAX_LENGTH) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          PHONE_NUMBER_FIELD,
          PHONE_NUMBER_MAX_LENGTH_MESSAGE.formatted(PHONE_NUMBER_MAX_LENGTH, phoneNumberLength)));
    }
  }

  private void validateMinLengthPhoneNumber(String phoneNumberValue) {
    var phoneNumberLength = phoneNumberValue.trim().length();
    if (phoneNumberLength < PHONE_NUMBER_MIN_LENGTH) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          PHONE_NUMBER_FIELD,
          PHONE_NUMBER_MIN_LENGTH_MESSAGE.formatted(PHONE_NUMBER_MIN_LENGTH, phoneNumberLength)));
    }
  }

  private void validateEmptyPhoneNumber(String phoneNumberValue) {
    if (phoneNumberValue.trim().isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          PHONE_NUMBER_FIELD, PHONE_NUMBER_NOT_EMPTY_MESSAGE));
    }
  }

  private void validateNullPhoneNumber(String phoneNumberValue) {
    if (phoneNumberValue == null) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(),
          PHONE_NUMBER_FIELD, PHONE_NUMBER_NOT_NULL_MESSAGE));
    }
  }

  public String getPhoneNumberValue() {
    return phoneNumberValue;
  }
}
