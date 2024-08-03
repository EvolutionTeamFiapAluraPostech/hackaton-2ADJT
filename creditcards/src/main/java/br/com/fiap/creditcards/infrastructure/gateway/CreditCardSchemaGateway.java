package br.com.fiap.creditcards.infrastructure.gateway;

import br.com.fiap.creditcards.application.gateway.CreditCardGateway;
import br.com.fiap.creditcards.domain.entity.CreditCard;
import br.com.fiap.creditcards.domain.exception.NoResultException;
import br.com.fiap.creditcards.domain.valueobject.Cpf;
import br.com.fiap.creditcards.domain.valueobject.CreditCardNumber;
import br.com.fiap.creditcards.infrastructure.repository.CreditCardRepository;
import br.com.fiap.creditcards.infrastructure.schema.CreditCardSchema;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

@Service
public class CreditCardSchemaGateway implements CreditCardGateway {

  public static final String CREDIT_CARD_NAO_ENCONTRADO_COM_ESTE_NUMERO_CPF_MESSAGE = "Cartão de crédito não encontrado com este número e cpf. Número %s e Cpf %s.";
  public static final String CREDIT_CARD_NUMERO_CPF_FIELD = "número e cpf";
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

  @Override
  public CreditCard findByNumberAndCpfRequired(String creditCardNumberValue, String cpfValue) {
    var creditCardNumber = new CreditCardNumber(creditCardNumberValue);
    var cpf = new Cpf(cpfValue);
    var creditCardSchemaOptional = creditCardRepository.findByNumberAndCpf(
        creditCardNumber.getNumber(), cpf.getCpfValue());
    return creditCardSchemaOptional.map(this::getCreditCardFrom).orElseThrow(
        () -> new NoResultException(
            new FieldError(this.getClass().getSimpleName(), CREDIT_CARD_NUMERO_CPF_FIELD,
                CREDIT_CARD_NAO_ENCONTRADO_COM_ESTE_NUMERO_CPF_MESSAGE.formatted(
                    creditCardNumber.getNumber(),
                    cpf.getCpfValue()))));
  }

  @Override
  public CreditCard updateLimit(CreditCard creditCard, BigDecimal newLimit) {
    var creditCardSchemaOptional = creditCardRepository.findByNumberAndCpf(
        creditCard.getNumber(), creditCard.getCpf());
    if (creditCardSchemaOptional.isPresent()) {
      var creditCardSchema = creditCardSchemaOptional.get();
      creditCardSchema.setLimitValue(newLimit);
      var creditCardSaved = creditCardRepository.save(creditCardSchema);
      return getCreditCardFrom(creditCardSaved);
    }
    return null;
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
