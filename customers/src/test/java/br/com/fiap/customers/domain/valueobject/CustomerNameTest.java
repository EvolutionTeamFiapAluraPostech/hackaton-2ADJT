package br.com.fiap.customers.domain.valueobject;


import static br.com.fiap.customers.domain.valueobject.CustomerName.NAME_MAX_LENGTH_INVALID_MESSAGE;
import static br.com.fiap.customers.domain.valueobject.CustomerName.NAME_MIN_LENGTH_INVALID_MESSAGE;
import static br.com.fiap.customers.domain.valueobject.CustomerName.NAME_NOT_EMPTY_MESSAGE;
import static br.com.fiap.customers.domain.valueobject.CustomerName.NAME_NOT_NULL_MESSAGE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.customers.domain.exception.ValidatorException;
import br.com.fiap.customers.shared.util.StringUtil;
import org.junit.jupiter.api.Test;

class CustomerNameTest {

  public static final String CUSTOMER_VALID_NAME = "Morpheus";

  @Test
  void shouldThrowValidatorExceptionWhenNameIsNull() {
    assertThatThrownBy(() -> new CustomerName(null))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(NAME_NOT_NULL_MESSAGE);
  }

  @Test
  void shouldThrowValidatorExceptionWhenNameIsEmpty() {
    assertThatThrownBy(() -> new CustomerName(""))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(NAME_NOT_EMPTY_MESSAGE);
  }

  @Test
  void shouldThrowValidatorExceptionWhenNameMinLengthIsInvalid() {
    assertThatThrownBy(() -> new CustomerName("A"))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(NAME_MIN_LENGTH_INVALID_MESSAGE);
  }

  @Test
  void shouldThrowValidatorExceptionWhenNameMaxLengthIsInvalid() {
    var nameWithInvalidMaxLength = StringUtil.generateStringLength(501);
    assertThatThrownBy(() -> new CustomerName(nameWithInvalidMaxLength))
        .isInstanceOf(ValidatorException.class)
        .hasMessage(NAME_MAX_LENGTH_INVALID_MESSAGE);
  }

  @Test
  void shouldValidateWhenNameIsValid() {
    var customerName = new CustomerName(CUSTOMER_VALID_NAME);

    assertThat(customerName).isNotNull();
    assertThat(customerName.getName()).isNotEmpty();
    assertThat(customerName.getName()).isEqualTo(CUSTOMER_VALID_NAME);
  }
}
