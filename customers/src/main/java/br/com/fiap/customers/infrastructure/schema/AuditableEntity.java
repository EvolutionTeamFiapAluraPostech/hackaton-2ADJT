package br.com.fiap.customers.infrastructure.schema;

public interface AuditableEntity {

  void setCreatedBy(String email);
  void setUpdatedBy(String email);
}
