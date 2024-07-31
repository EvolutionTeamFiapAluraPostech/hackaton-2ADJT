package br.com.fiap.customers.domain.entity;

import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CITY;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_COUNTRY;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CPF;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_EMAIL;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_ID;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_NAME;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_PHONE_NUMBER;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_POSTAL_CODE;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_STATE;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_STREET;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import br.com.fiap.customers.domain.exception.ValidatorException;
import br.com.fiap.customers.shared.util.StringUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

class CustomerTest {

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"A"})
  void shouldThrowValidatorExceptionWhenCustomerNameIsInvalid(String name) {
    assertThatThrownBy(() -> new Customer(name, "", "", "",
        "", "", "", "", ""))
        .isInstanceOf(ValidatorException.class);
  }

  @Test
  void shouldThrowValidatorExceptionWhenCustomerNameMaxLengthIsInvalid() {
    var name = StringUtil.generateStringLength(501);
    assertThatThrownBy(() -> new Customer(name, "", "", "",
        "", "", "", "", ""))
        .isInstanceOf(ValidatorException.class);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"A", "1", "11111111111", "1234567890", "123456789012"})
  void shouldThrowValidatorExceptionWhenCustomerCpfIsInvalid(String cpf) {
    assertThatThrownBy(() -> new Customer(DEFAULT_CUSTOMER_NAME, cpf, "",
        "", "", "", "", "", ""))
        .isInstanceOf(ValidatorException.class);
  }

  @ParameterizedTest
  @NullAndEmptySource
  @ValueSource(strings = {"email.domain.com", " email.domain.com", "@", "1", "email@domain",
      "A@b@c@example.com", "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com", "email @example.com"})
  void shouldThrowValidatorExceptionWhenCustomerEmailIsInvalid(String email) {
    assertThatThrownBy(
        () -> new Customer(DEFAULT_CUSTOMER_NAME, DEFAULT_CUSTOMER_CPF, email,
            "", "", "", "", "", ""))
        .isInstanceOf(ValidatorException.class);
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldThrowValidatorExceptionWhenCustomerPhoneNumberIsInvalid(String phoneNumber) {
    assertThatThrownBy(
        () -> new Customer(DEFAULT_CUSTOMER_NAME, DEFAULT_CUSTOMER_CPF,
            DEFAULT_CUSTOMER_EMAIL, phoneNumber, "", "", "", "", ""))
        .isInstanceOf(ValidatorException.class);
  }

  @Test
  void shouldCreateNewCustomer() {
    var customer = new Customer(DEFAULT_CUSTOMER_ID, DEFAULT_CUSTOMER_NAME, DEFAULT_CUSTOMER_CPF,
        DEFAULT_CUSTOMER_EMAIL, DEFAULT_CUSTOMER_PHONE_NUMBER,
        DEFAULT_CUSTOMER_STREET, DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY, DEFAULT_CUSTOMER_POSTAL_CODE);

    assertThat(customer).isNotNull();
    assertThat(customer.getId()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_CUSTOMER_ID);
    assertThat(customer.getName()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_CUSTOMER_NAME);
    assertThat(customer.getCpf()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_CUSTOMER_CPF);
    assertThat(customer.getEmail()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_CUSTOMER_EMAIL);
    assertThat(customer.getPhoneNumber()).isNotNull().isNotEmpty()
        .isEqualTo(DEFAULT_CUSTOMER_PHONE_NUMBER);
    assertThat(customer.getStreet()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_CUSTOMER_STREET);
    assertThat(customer.getCity()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_CUSTOMER_CITY);
    assertThat(customer.getState()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_CUSTOMER_STATE);
    assertThat(customer.getCountry()).isNotNull().isNotEmpty().isEqualTo(DEFAULT_CUSTOMER_COUNTRY);
    assertThat(customer.getPostalCode()).isNotNull().isNotEmpty()
        .isEqualTo(DEFAULT_CUSTOMER_POSTAL_CODE);
  }
}
