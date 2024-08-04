package br.com.fiap.payments.infrastructure.schema;

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
@Table(schema = "payments_management", name = "payments")
public class PaymentSchema extends BaseSchema {

  private String cpf;
  private String number;
  private String expirationDate;
  private String cvv;
  private BigDecimal value;
  private String status;
}
