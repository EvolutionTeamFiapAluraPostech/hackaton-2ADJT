package br.com.fiap.payments.infrastructure.repository;

import br.com.fiap.payments.infrastructure.schema.PaymentSchema;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentSchemaRepository extends JpaRepository<PaymentSchema, UUID> {

}
