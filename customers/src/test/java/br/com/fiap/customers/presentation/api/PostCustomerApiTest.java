package br.com.fiap.customers.presentation.api;


import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CPF_ALREADY_POPULATED_IN_DATABASE;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CITY;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_COUNTRY;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CPF;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_EMAIL;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_NAME;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_PHONE_NUMBER;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_POSTAL_CODE;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_STATE;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_STREET;
import static br.com.fiap.customers.shared.util.IsUUID.isUUID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.customers.infrastructure.schema.CustomerSchema;
import br.com.fiap.customers.presentation.dto.CustomerInputDto;
import br.com.fiap.customers.shared.annotation.DatabaseTest;
import br.com.fiap.customers.shared.annotation.IntegrationTest;
import br.com.fiap.customers.shared.api.JsonUtil;
import br.com.fiap.customers.shared.util.StringUtil;
import com.jayway.jsonpath.JsonPath;
import jakarta.persistence.EntityManager;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

@IntegrationTest
@DatabaseTest
class PostCustomerApiTest {

  private static final String URL_CUSTOMERS = "/api/cliente";
  private final MockMvc mockMvc;
  private final EntityManager entityManager;

  @Autowired
  PostCustomerApiTest(MockMvc mockMvc, EntityManager entityManager) {
    this.mockMvc = mockMvc;
    this.entityManager = entityManager;
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenCpfIsNotPresentOrIsInvalid(String cpf) throws Exception {
    var customer = new CustomerInputDto(cpf, DEFAULT_CUSTOMER_NAME, DEFAULT_CUSTOMER_EMAIL,
        DEFAULT_CUSTOMER_PHONE_NUMBER, DEFAULT_CUSTOMER_STREET,
        DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY, DEFAULT_CUSTOMER_POSTAL_CODE);
    var customerJson = JsonUtil.toJson(customer);

    var request = post(URL_CUSTOMERS)
        .contentType(APPLICATION_JSON)
        .content(customerJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @ValueSource(strings = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"})
  void shouldReturnBadRequestWhenCpfIsInvalid(String number) throws Exception {
    var cpf = StringUtil.generateStringRepeatCharLength(number, 11);
    var customer = new CustomerInputDto(cpf, DEFAULT_CUSTOMER_NAME,
        DEFAULT_CUSTOMER_EMAIL, DEFAULT_CUSTOMER_PHONE_NUMBER,
        DEFAULT_CUSTOMER_STREET, DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY, DEFAULT_CUSTOMER_POSTAL_CODE);
    var customerJson = JsonUtil.toJson(customer);

    var request = post(URL_CUSTOMERS)
        .contentType(APPLICATION_JSON)
        .content(customerJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnConflictWhenCpfAlreadyExists() throws Exception {
    var customer = new CustomerInputDto(DEFAULT_CUSTOMER_CPF_ALREADY_POPULATED_IN_DATABASE,
        DEFAULT_CUSTOMER_NAME, DEFAULT_CUSTOMER_EMAIL, DEFAULT_CUSTOMER_PHONE_NUMBER,
        DEFAULT_CUSTOMER_STREET, DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_POSTAL_CODE, DEFAULT_CUSTOMER_COUNTRY);
    var customerJson = JsonUtil.toJson(customer);

    var request = post(URL_CUSTOMERS)
        .contentType(APPLICATION_JSON)
        .content(customerJson);
    mockMvc.perform(request)
        .andExpect(status().isConflict());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenNomeIsNotPresent(String nome) throws Exception {
    var customer = new CustomerInputDto(DEFAULT_CUSTOMER_CPF, nome,
        DEFAULT_CUSTOMER_EMAIL, DEFAULT_CUSTOMER_PHONE_NUMBER,
        DEFAULT_CUSTOMER_STREET, DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY, DEFAULT_CUSTOMER_POSTAL_CODE);
    var customerJson = JsonUtil.toJson(customer);

    var request = post(URL_CUSTOMERS)
        .contentType(APPLICATION_JSON)
        .content(customerJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenNomeMinLengthIsInvalid() throws Exception {
    var name = "A";
    var customer = new CustomerInputDto(DEFAULT_CUSTOMER_CPF, name,
        DEFAULT_CUSTOMER_EMAIL, DEFAULT_CUSTOMER_PHONE_NUMBER,
        DEFAULT_CUSTOMER_STREET, DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY, DEFAULT_CUSTOMER_POSTAL_CODE);
    var customerJson = JsonUtil.toJson(customer);

    var request = post(URL_CUSTOMERS)
        .contentType(APPLICATION_JSON)
        .content(customerJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenNomeMaxLengthIsInvalid() throws Exception {
    var name = StringUtil.generateStringLength(501);
    var customer = new CustomerInputDto(DEFAULT_CUSTOMER_CPF, name,
        DEFAULT_CUSTOMER_EMAIL, DEFAULT_CUSTOMER_PHONE_NUMBER,
        DEFAULT_CUSTOMER_STREET, DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY, DEFAULT_CUSTOMER_POSTAL_CODE);
    var customerJson = JsonUtil.toJson(customer);

    var request = post(URL_CUSTOMERS)
        .contentType(APPLICATION_JSON)
        .content(customerJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenEmailIsNotPresent(String email) throws Exception {
    var customer = new CustomerInputDto(DEFAULT_CUSTOMER_CPF, DEFAULT_CUSTOMER_NAME,
        email, DEFAULT_CUSTOMER_PHONE_NUMBER,
        DEFAULT_CUSTOMER_STREET, DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY, DEFAULT_CUSTOMER_POSTAL_CODE);
    var customerJson = JsonUtil.toJson(customer);

    var request = post(URL_CUSTOMERS)
        .contentType(APPLICATION_JSON)
        .content(customerJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenEmailMinLengthIsInvalid() throws Exception {
    var email = "A";
    var customer = new CustomerInputDto(DEFAULT_CUSTOMER_CPF, DEFAULT_CUSTOMER_NAME,
        email, DEFAULT_CUSTOMER_PHONE_NUMBER,
        DEFAULT_CUSTOMER_STREET, DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY, DEFAULT_CUSTOMER_POSTAL_CODE);
    var customerJson = JsonUtil.toJson(customer);

    var request = post(URL_CUSTOMERS)
        .contentType(APPLICATION_JSON)
        .content(customerJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnBadRequestWhenEmailMaxLengthIsInvalid() throws Exception {
    var email = StringUtil.generateStringLength(501);
    var customer = new CustomerInputDto(DEFAULT_CUSTOMER_CPF, DEFAULT_CUSTOMER_NAME,
        email, DEFAULT_CUSTOMER_PHONE_NUMBER,
        DEFAULT_CUSTOMER_STREET, DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY, DEFAULT_CUSTOMER_POSTAL_CODE);
    var customerJson = JsonUtil.toJson(customer);

    var request = post(URL_CUSTOMERS)
        .contentType(APPLICATION_JSON)
        .content(customerJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @ValueSource(strings = {"email.domain.com", " email.domain.com", "@", "1", "email@domain",
      "A@b@c@example.com", "a\"b(c)d,e:f;g<h>i[j\\k]l@example.com", "email @example.com"})
  void shouldReturnBadRequestWhenEmailIsInvalid(String email) throws Exception {
    var customer = new CustomerInputDto(DEFAULT_CUSTOMER_CPF, DEFAULT_CUSTOMER_NAME,
        email, DEFAULT_CUSTOMER_PHONE_NUMBER,
        DEFAULT_CUSTOMER_STREET, DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY, DEFAULT_CUSTOMER_POSTAL_CODE);
    var customerJson = JsonUtil.toJson(customer);

    var request = post(URL_CUSTOMERS)
        .contentType(APPLICATION_JSON)
        .content(customerJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @ParameterizedTest
  @NullAndEmptySource
  void shouldReturnBadRequestWhenTelefoneIsNotPresent(String telefone) throws Exception {
    var customer = new CustomerInputDto(DEFAULT_CUSTOMER_CPF, DEFAULT_CUSTOMER_NAME,
        DEFAULT_CUSTOMER_EMAIL, telefone,
        DEFAULT_CUSTOMER_STREET, DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_COUNTRY, DEFAULT_CUSTOMER_POSTAL_CODE);
    var customerJson = JsonUtil.toJson(customer);

    var request = post(URL_CUSTOMERS)
        .contentType(APPLICATION_JSON)
        .content(customerJson);
    mockMvc.perform(request)
        .andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturnOkWhenNewCustomerWasCreated() throws Exception {
    var customer = new CustomerInputDto(DEFAULT_CUSTOMER_CPF, DEFAULT_CUSTOMER_NAME,
        DEFAULT_CUSTOMER_EMAIL, DEFAULT_CUSTOMER_PHONE_NUMBER, DEFAULT_CUSTOMER_STREET,
        DEFAULT_CUSTOMER_CITY, DEFAULT_CUSTOMER_STATE,
        DEFAULT_CUSTOMER_POSTAL_CODE, DEFAULT_CUSTOMER_COUNTRY);
    var customerJson = JsonUtil.toJson(customer);

    var request = post(URL_CUSTOMERS)
        .contentType(APPLICATION_JSON)
        .content(customerJson);
    var mvcResult = mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.idCliente", isUUID()))
        .andReturn();

    var contentAsString = mvcResult.getResponse().getContentAsString();
    var id = JsonPath.parse(contentAsString).read("$.idCliente").toString();
    var customerFound = entityManager.find(CustomerSchema.class, UUID.fromString(id));
    assertThat(customerFound).isNotNull();
    assertThat(customerFound.getName()).isNotNull().isEqualTo(customer.nome());
    assertThat(customerFound.getCpf()).isNotNull().isEqualTo(customer.cpf());
    assertThat(customerFound.getEmail()).isNotNull().isEqualTo(customer.email());
    assertThat(customerFound.getPhoneNumber()).isNotNull().isEqualTo(customer.telefone());
    assertThat(customerFound.getStreet()).isNotNull().isEqualTo(customer.rua());
    assertThat(customerFound.getCity()).isNotNull().isEqualTo(customer.cidade());
    assertThat(customerFound.getState()).isNotNull().isEqualTo(customer.estado());
    assertThat(customerFound.getCountry()).isNotNull().isEqualTo(customer.pais());
    assertThat(customerFound.getPostalCode()).isNotNull().isEqualTo(customer.cep());
  }
}
