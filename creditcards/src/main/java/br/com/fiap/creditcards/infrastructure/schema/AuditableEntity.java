package br.com.fiap.creditcards.infrastructure.schema;

public interface AuditableEntity {

  void setCreatedBy(String email);
  void setUpdatedBy(String email);
}
