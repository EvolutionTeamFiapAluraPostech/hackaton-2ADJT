package br.com.fiap.creditcards.infrastructure.httpclient.customer;

import br.com.fiap.creditcards.infrastructure.configuration.FeignConfiguration;
import br.com.fiap.creditcards.infrastructure.httpclient.customer.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "customers", url = "${base.url.http-customer}", configuration = FeignConfiguration.class)
public interface CustomerHttpClient {

  @GetMapping("/api/cliente/{cpf}")
  ResponseEntity<CustomerDto> getCustomerByCpf(@PathVariable String cpf);
}
