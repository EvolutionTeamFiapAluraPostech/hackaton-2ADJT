package br.com.fiap.creditcards.infrastructure.gateway;

import br.com.fiap.creditcards.application.gateway.CreditCardGateway;
import br.com.fiap.creditcards.domain.entity.CreditCard;
import br.com.fiap.creditcards.infrastructure.repository.CreditCardRepository;
import br.com.fiap.creditcards.infrastructure.schema.CreditCardSchema;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CreditCardSchemaGateway implements CreditCardGateway {

  private final CreditCardRepository creditCardRepository;

  public CreditCardSchemaGateway(CreditCardRepository creditCardRepository) {
    this.creditCardRepository = creditCardRepository;
  }

  @Override
  public CreditCard save(CreditCard creditCard) {
    var creditCardSchema = createNewCreditCardSchemaFrom(creditCard);
    var creditCardSaved = creditCardRepository.save(creditCardSchema);
    return getCreditCardFrom(creditCardSaved);
  }

  @Override
  public CreditCard findByNumber(String number) {
    var creditCardSchema = creditCardRepository.findByNumber(number);
    return creditCardSchema.map(this::getCreditCardFrom).orElse(null);
  }

  @Override
  public List<CreditCard> findByCpf(String cpf) {
    var creditCardsSchema = creditCardRepository.findByCpf(cpf);
    if (creditCardsSchema.isEmpty()) {
      return Collections.emptyList();
    }
    return creditCardsSchema.stream().map(this::getCreditCardFrom).toList();
  }

  private CreditCard getCreditCardFrom(CreditCardSchema creditCardSaved) {
    return new CreditCard(creditCardSaved.getId().toString(),
        creditCardSaved.getCpf(),
        String.valueOf(creditCardSaved.getLimitValue()),
        creditCardSaved.getNumber(),
        creditCardSaved.getExpirationDate(),
        creditCardSaved.getCvv());
  }

  private static CreditCardSchema createNewCreditCardSchemaFrom(CreditCard creditCard) {
    return CreditCardSchema.builder()
        .cpf(creditCard.getCpf())
        .limitValue(new BigDecimal(creditCard.getLimit()))
        .number(creditCard.getNumber())
        .expirationDate(creditCard.getExpirationDate())
        .cvv(creditCard.getCvv())
        .build();
  }
}
