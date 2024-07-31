package br.com.fiap.creditcards.infrastructure.gateway;

import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.DEFAULT_CREDIT_CARD_NUMBER;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.createCreditCard;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.createCreditCardSchemaFrom;
import static br.com.fiap.creditcards.shared.testdata.CreditCardTestData.createNewCreditCard;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.creditcards.infrastructure.repository.CreditCardRepository;
import br.com.fiap.creditcards.infrastructure.schema.CreditCardSchema;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreditCardSchemaGatewayTest {

  @Mock
  private CreditCardRepository creditCardRepository;
  @InjectMocks
  private CreditCardSchemaGateway creditCardSchemaGateway;

  @Test
  void shouldSaveCreditCardWhenAllAttributesAreCorrect() {
    var creditCard = createNewCreditCard();
    var creditCardWithId = createCreditCard();
    var creditCardSchemaWithId = createCreditCardSchemaFrom(creditCard);
    when(creditCardRepository.save(any(CreditCardSchema.class))).thenReturn(creditCardSchemaWithId);

    var creditCardSaved = creditCardSchemaGateway.save(creditCard);

    assertThat(creditCardSaved).isNotNull();
    assertThat(creditCardSaved).usingRecursiveComparison().isEqualTo(creditCardWithId);
  }

  @Test
  void shouldFindCreditCardByNumber() {
    var creditCard = createCreditCard();
    var creditCardSchema = createCreditCardSchemaFrom(creditCard);
    when(creditCardRepository.findByNumber(creditCard.getNumber())).thenReturn(
        Optional.of(creditCardSchema));

    var creditCardFound = creditCardSchemaGateway.findByNumber(creditCard.getNumber());

    assertThat(creditCardFound).isNotNull();
    assertThat(creditCardFound.getId()).isNotNull().isEqualTo(creditCard.getId());
    assertThat(creditCardFound).usingRecursiveComparison().isEqualTo(creditCard);
  }

  @Test
  void shouldReturnNullWhenCreditCardWasNotFoundByNumber() {
    when(creditCardRepository.findByNumber(DEFAULT_CREDIT_CARD_NUMBER)).thenReturn(
        Optional.empty());

    var creditCardFound = creditCardSchemaGateway.findByNumber(DEFAULT_CREDIT_CARD_NUMBER);

    assertThat(creditCardFound).isNull();
  }

  @Test
  void shouldFindCreditCardListByCpf() {
    var creditCard = createCreditCard();
    var creditCardSchema = createCreditCardSchemaFrom(creditCard);
    when(creditCardRepository.findByCpf(creditCard.getCpf())).thenReturn(List.of(creditCardSchema));

    var creditCards = creditCardSchemaGateway.findByCpf(creditCard.getCpf());

    assertThat(creditCards).isNotNull().isNotEmpty().hasSize(1);
  }

  @Test
  void shouldReturnEmptyListWhenCreditCardWasNotFoundByCpf() {
    var creditCard = createCreditCard();
    when(creditCardRepository.findByCpf(creditCard.getCpf())).thenReturn(Collections.emptyList());

    var creditCards = creditCardSchemaGateway.findByCpf(creditCard.getCpf());

    assertThat(creditCards).isEmpty();
  }
}
