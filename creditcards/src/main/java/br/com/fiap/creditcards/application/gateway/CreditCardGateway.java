package br.com.fiap.creditcards.application.gateway;

import br.com.fiap.creditcards.domain.entity.CreditCard;
import java.math.BigDecimal;
import java.util.List;

public interface CreditCardGateway {

  CreditCard save(CreditCard creditCard);

  CreditCard findByNumber(String number);

  List<CreditCard> findByCpf(String cpf);

  CreditCard findByNumberAndCpfRequired
      (String creditCardNumber, String cpf);

  CreditCard updateLimit(CreditCard creditCard, BigDecimal newLimit);
}
