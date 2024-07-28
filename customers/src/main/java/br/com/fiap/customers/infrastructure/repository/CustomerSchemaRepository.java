package br.com.fiap.customers.infrastructure.repository;

import br.com.fiap.customers.infrastructure.schema.CustomerSchema;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerSchemaRepository extends JpaRepository<CustomerSchema, UUID> {

  Optional<CustomerSchema> findByCpf(String cpf);
}
