package br.com.fiap.customers.domain.valueobject;

import static br.com.fiap.customers.domain.valueobject.PhoneNumber.PHONE_NUMBER_NOT_EMPTY_MESSAGE;
import static br.com.fiap.customers.domain.valueobject.PhoneNumber.PHONE_NUMBER_NOT_NULL_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.fiap.customers.domain.exception.ValidatorException;
import br.com.fiap.customers.shared.util.StringUtil;
import org.junit.jupiter.api.Test;

class PhoneNumberTest {

  public static final String VALID_PHONE_NUMBER = "5511912345678";

  @Test
  void shouldThrowValidatorExceptionWhenPhoneNumberIsNull() {
    assertThatThrownBy(() -> new PhoneNumber(null))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(PHONE_NUMBER_NOT_NULL_MESSAGE);
  }

  @Test
  void shouldThrowValidatorExceptionWhenPhoneNumberIsEmpty() {
    assertThatThrownBy(() -> new PhoneNumber(""))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(PHONE_NUMBER_NOT_EMPTY_MESSAGE);
  }

  @Test
  void shouldThrowValidatorExceptionWhenPhoneNumberMinLengthIsInvalid() {
    var phoneNumber = "123456";
    assertThrows(ValidatorException.class, () -> new PhoneNumber(phoneNumber));
  }

  @Test
  void shouldThrowExceptionWhenEmailMaxLengthIsInvalid() {
    var phoneNumber = StringUtil.generateStringLength(51);
    assertThrows(ValidatorException.class, () -> new PhoneNumber(phoneNumber));
  }

  @Test
  void shouldValidatePhoneNumber() {
    var phoneNumber = new PhoneNumber(VALID_PHONE_NUMBER);

    assertThat(phoneNumber).isNotNull();
    assertThat(phoneNumber.getPhoneNumberValue()).isNotNull().isNotEmpty();
    assertThat(phoneNumber.getPhoneNumberValue()).isEqualTo(VALID_PHONE_NUMBER);
  }
}
