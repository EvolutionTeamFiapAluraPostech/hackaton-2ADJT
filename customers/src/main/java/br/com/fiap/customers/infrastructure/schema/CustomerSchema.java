package br.com.fiap.customers.infrastructure.schema;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
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
@Table(schema = "customer_management", name = "customers")
public class CustomerSchema extends BaseSchema {

  private String name;
  private String email;
  private String cpf;
  private String phoneNumber;
  private String street;
  private String city;
  private String state;
  private String country;
  private String postalCode;

}
