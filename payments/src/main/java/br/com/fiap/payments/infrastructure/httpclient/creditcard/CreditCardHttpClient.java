package br.com.fiap.payments.infrastructure.httpclient.creditcard;

import br.com.fiap.payments.infrastructure.httpclient.configuration.FeignConfiguration;
import br.com.fiap.payments.infrastructure.httpclient.creditcard.dto.CreditCardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "creditcards", url = "base.url.http-creditcards", configuration = FeignConfiguration.class)
public interface CreditCardHttpClient {

  @GetMapping("/api/cartao/{number}/cliente/{cpf}")
  ResponseEntity<CreditCardDto> getCreditCardByNumberAndCpf(@PathVariable String number,
      @PathVariable String cpf);
}
