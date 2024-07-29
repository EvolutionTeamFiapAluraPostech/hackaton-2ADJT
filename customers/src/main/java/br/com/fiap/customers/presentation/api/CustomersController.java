package br.com.fiap.customers.presentation.api;

import br.com.fiap.customers.application.usecase.CreateCustomerUseCase;
import br.com.fiap.customers.application.usecase.GetCustomerByCpfUseCase;
import br.com.fiap.customers.presentation.dto.CustomerInputDto;
import br.com.fiap.customers.presentation.dto.CustomerOutputDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cliente")
public class CustomersController implements CustomersApi {

  private final CreateCustomerUseCase createCustomerUseCase;
  private final GetCustomerByCpfUseCase getCustomerByCpfUseCase;

  public CustomersController(CreateCustomerUseCase createCustomerUseCase,
      GetCustomerByCpfUseCase getCustomerByCpfUseCase) {
    this.createCustomerUseCase = createCustomerUseCase;
    this.getCustomerByCpfUseCase = getCustomerByCpfUseCase;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.OK)
  @Override
  public CustomerOutputDto postCustomer(@RequestBody CustomerInputDto customerInputDto) {
    var customer = createCustomerUseCase.execute(customerInputDto);
    return new CustomerOutputDto(customer.getId());
  }

  @GetMapping("/{cpf}")
  @ResponseStatus(HttpStatus.OK)
  @Override
  public CustomerOutputDto getCustomerByCpf(@PathVariable String cpf) {
    var customer = getCustomerByCpfUseCase.execute(cpf);
    return new CustomerOutputDto(customer.getId());
  }
}
