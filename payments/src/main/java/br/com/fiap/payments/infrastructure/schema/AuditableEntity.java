package br.com.fiap.payments.infrastructure.schema;

public interface AuditableEntity {

  void setCreatedBy(String email);
  void setUpdatedBy(String email);
}
