package br.com.fiap.customers.presentation.api;

import br.com.fiap.customers.application.usecase.CreateCustomerUseCase;
import br.com.fiap.customers.presentation.dto.CustomerInputDto;
import br.com.fiap.customers.presentation.dto.CustomerOutputDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cliente")
public class CustomersController implements CustomersApi {

  private final CreateCustomerUseCase createCustomerUseCase;

  public CustomersController(CreateCustomerUseCase createCustomerUseCase) {
    this.createCustomerUseCase = createCustomerUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Override
  public CustomerOutputDto postCustomer(@RequestBody CustomerInputDto customerInputDto) {
    var customer = createCustomerUseCase.execute(customerInputDto);
    return new CustomerOutputDto(customer.getId());
  }
}
