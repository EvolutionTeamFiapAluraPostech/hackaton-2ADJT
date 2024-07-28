package br.com.fiap.customers.domain.valueobject;

import static br.com.fiap.customers.domain.valueobject.Cpf.CPF_INVALID;
import static br.com.fiap.customers.domain.valueobject.Cpf.CPF_LENGTH_INVALID_MESSAGE;
import static br.com.fiap.customers.domain.valueobject.Cpf.CPF_NOT_EMPTY_MESSAGE;
import static br.com.fiap.customers.domain.valueobject.Cpf.CPF_NOT_NULL_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.customers.domain.exception.ValidatorException;
import br.com.fiap.customers.shared.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CpfTest {

  public static final String CPF_VALUE = "18939181069";

  @Test
  void shouldThrowValidatorExceptionWhenCpfValueIsNull() {
    assertThatThrownBy(() -> new Cpf(null))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(CPF_NOT_NULL_MESSAGE);
  }

  @Test
  void shouldThrowValidatorExceptionWhenCpfValueIsEmpty() {
    assertThatThrownBy(() -> new Cpf(""))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(CPF_NOT_EMPTY_MESSAGE);
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "12", "13"})
  void shouldThrowValidatorExceptionWhenCpfValueMinLengthIsInvalid(String cpfValueMinLength) {
    var cpfValue = StringUtil.generateStringRepeatCharLength(cpfValueMinLength, Integer.valueOf(cpfValueMinLength));
    var cpfLength = cpfValue.length();
    assertThatThrownBy(() -> new Cpf(cpfValue))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(CPF_LENGTH_INVALID_MESSAGE.formatted(cpfLength));
  }

  @ParameterizedTest
  @ValueSource(strings = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "0"})
  void shouldThrowValidatorExceptionWhenCpfValueWithEqualNumbersIsInvalid(String cpfValue) {
    var cpfGenerated = StringUtil.generateStringRepeatCharLength(cpfValue, 11);
    assertThatThrownBy(() -> new Cpf(cpfGenerated))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(CPF_INVALID.formatted(cpfGenerated));
  }

  @ParameterizedTest
  @ValueSource(strings = {"72387289316", "18939181068", "12345678901"})
  void shouldThrowValidatorExceptionWhenCpfValueInvalid(String cpfValue) {
    assertThatThrownBy(() -> new Cpf(cpfValue))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(CPF_INVALID.formatted(cpfValue));
  }

  @Test
  void shouldValidateCpfNumberWhenCpfNumberIsValid() {
    var cpf = new Cpf(CPF_VALUE);

    assertThat(cpf).isNotNull();
    assertThat(cpf.getCpfValue()).isNotNull().isNotEmpty();
    assertThat(cpf.getCpfValue()).isEqualTo(CPF_VALUE);
  }
}
