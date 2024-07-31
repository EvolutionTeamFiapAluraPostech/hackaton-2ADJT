package br.com.fiap.creditcards.shared.testdata;

import br.com.fiap.creditcards.domain.entity.CreditCard;
import br.com.fiap.creditcards.infrastructure.schema.CreditCardSchema;
import br.com.fiap.creditcards.presentation.dto.CreditCardInputDto;
import java.math.BigDecimal;
import java.util.UUID;

public final class CreditCardTestData {

  public static final String DEFAULT_CREDIT_CARD_ID = "fc9ca35f-e911-4bdd-9b73-a6ddc622d0fd";
  public static final UUID DEFAULT_CREDIT_CARD_UUID = UUID.fromString(DEFAULT_CREDIT_CARD_ID);
  public static final String DEFAULT_CREDIT_CARD_CPF = "84527263846";
  public static final String DEFAULT_CREDIT_CARD_LIMIT = "1000.00";
  public static final String DEFAULT_CREDIT_CARD_NUMBER = "1234567890123456";
  public static final String DEFAULT_CREDIT_CARD_EXPIRATION_DATE = "12/24";
  public static final String DEFAULT_CREDIT_CARD_CVV = "123";

  public static CreditCardInputDto createNewCreditCardInputDto() {
    return new CreditCardInputDto(DEFAULT_CREDIT_CARD_CPF,
        DEFAULT_CREDIT_CARD_LIMIT, DEFAULT_CREDIT_CARD_NUMBER,
        DEFAULT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_CREDIT_CARD_CVV);
  }

  public static CreditCard createNewCreditCard() {
    return new CreditCard(null, DEFAULT_CREDIT_CARD_CPF,
        DEFAULT_CREDIT_CARD_LIMIT, DEFAULT_CREDIT_CARD_NUMBER,
        DEFAULT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_CREDIT_CARD_CVV);
  }

  public static CreditCard createCreditCard() {
    return new CreditCard(DEFAULT_CREDIT_CARD_ID, DEFAULT_CREDIT_CARD_CPF,
        DEFAULT_CREDIT_CARD_LIMIT, DEFAULT_CREDIT_CARD_NUMBER,
        DEFAULT_CREDIT_CARD_EXPIRATION_DATE, DEFAULT_CREDIT_CARD_CVV);
  }

  public static CreditCardSchema createNewCreditCardSchemaFrom(CreditCard creditCard) {
    return CreditCardSchema.builder()
        .id(null)
        .cpf(creditCard.getCpf())
        .limitValue(new BigDecimal(creditCard.getLimit()))
        .number(creditCard.getNumber())
        .expirationDate(creditCard.getExpirationDate())
        .cvv(creditCard.getCvv())
        .build();
  }

  public static CreditCardSchema createCreditCardSchemaFrom(CreditCard creditCard) {
    var creditCardSchema = createNewCreditCardSchemaFrom(creditCard);
    creditCardSchema.setId(DEFAULT_CREDIT_CARD_UUID);
    return creditCardSchema;
  }

  private CreditCardTestData(){
  }
}
