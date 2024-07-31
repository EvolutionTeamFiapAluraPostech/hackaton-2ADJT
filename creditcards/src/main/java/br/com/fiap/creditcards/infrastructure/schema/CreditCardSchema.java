package br.com.fiap.creditcards.infrastructure.schema;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(schema = "creditcard_management", name = "credit_cards")
public class CreditCardSchema extends BaseSchema {

  private String cpf;
  private BigDecimal limitValue;
  private String number;
  private String expirationDate;
  private String cvv;
}
