package br.com.fiap.customers.presentation.api;

import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_CPF_ALREADY_POPULATED_IN_DATABASE;
import static br.com.fiap.customers.shared.testdata.CustomerTestData.DEFAULT_CUSTOMER_ID;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.customers.shared.annotation.DatabaseTest;
import br.com.fiap.customers.shared.annotation.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@IntegrationTest
@DatabaseTest
class GetCustomerByCpfApiTest {

  private static final String URL_CUSTOMERS = "/api/cliente/{cpf}";
  private final MockMvc mockMvc;

  @Autowired
  GetCustomerByCpfApiTest(MockMvc mockMvc) {
    this.mockMvc = mockMvc;
  }

  @ParameterizedTest
  @ValueSource(strings = {"72387289316", "189391810Ba", "123.456.789-01"})
  void shouldReturnInternalServerErrorWhenCustomerCpfIsInvalid(String cpf) throws Exception {
    var request = get(URL_CUSTOMERS, cpf);
    mockMvc.perform(request)
        .andExpect(MockMvcResultMatchers.status().isInternalServerError())
        .andExpect(content().contentType(APPLICATION_JSON));
  }

  @Test
  void shouldReturnOkWhenCustomerWasFoundByCpf() throws Exception {
    var request = get(URL_CUSTOMERS, DEFAULT_CUSTOMER_CPF_ALREADY_POPULATED_IN_DATABASE);
    mockMvc.perform(request)
        .andExpect(status().isOk())
        .andExpect(content().contentType(APPLICATION_JSON))
        .andExpect(jsonPath("$.id_cliente", equalTo(DEFAULT_CUSTOMER_ID)));
  }
}
