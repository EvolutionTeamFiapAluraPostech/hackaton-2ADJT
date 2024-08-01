package br.com.fiap.payments.domain.valueobject;

import br.com.fiap.payments.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class Cpf {

  public static final String CPF_FIELD = "cpf";
  public static final String CPF_NOT_NULL_MESSAGE = "O CPF não pode ser nulo.";
  public static final String CPF_NOT_EMPTY_MESSAGE = "O CPF não pode ser vazio.";
  public static final String CPF_LENGTH_INVALID_MESSAGE = "O tamanho do CPF é inválido. O tamanho correto é 11, mas foi informado %s";
  public static final String CPF_INVALID = "CPF inválido %s.";
  private final String cpfValue;

  public Cpf(String cpfValue) {
    this.validateNotNullOf(cpfValue);
    this.validateNotEmptyOf(cpfValue);
    this.validateLengthOf(cpfValue);
    this.validate(cpfValue);
    this.cpfValue = cpfValue;
  }

  private void validate(String rawCpf) {
    String cpf = removeNonDigits(rawCpf);
    if (hasAllDigitsEqual(cpf)) {
      throwCpfInvalidException(cpf);
    }
    int digit1 = calculateDigit(cpf, 10);
    int digit2 = calculateDigit(cpf, 11);
    if (!extractDigit(cpf).equals(String.valueOf(digit1) + digit2)) {
      throwCpfInvalidException(cpf);
    }
  }

  private void throwCpfInvalidException(String cpf) {
    throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), CPF_FIELD,
        CPF_INVALID.formatted(cpf)));
  }

  private static String removeNonDigits(String cpf) {
    return cpf.replaceAll("\\D", "");
  }

  private static boolean hasAllDigitsEqual(String cpf) {
    char firstCpfDigit = cpf.charAt(0);
    return cpf.chars().allMatch(digit -> digit == firstCpfDigit);
  }

  private static int calculateDigit(String cpf, int factor) {
    int total = 0;
    for (char digit : cpf.toCharArray()) {
      if (factor > 1) {
        total += Character.getNumericValue(digit) * factor--;
      }
    }
    int rest = total % 11;
    return (rest < 2) ? 0 : 11 - rest;
  }

  private static String extractDigit(String cpf) {
    return cpf.substring(9);
  }

  private void validateLengthOf(String cpfValue) {
    var cpfLength = cpfValue.trim().length();
    if (cpfValue.trim().length() != 11) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), CPF_FIELD,
          CPF_LENGTH_INVALID_MESSAGE.formatted(cpfLength)));
    }
  }

  private void validateNotEmptyOf(String cpfValue) {
    if (cpfValue.trim().isEmpty()) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), CPF_FIELD,
          CPF_NOT_EMPTY_MESSAGE));
    }
  }

  private void validateNotNullOf(String cpfValue) {
    if (cpfValue == null) {
      throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), CPF_FIELD,
          CPF_NOT_NULL_MESSAGE));
    }
  }

  public String getCpfValue() {
    return cpfValue;
  }
}
