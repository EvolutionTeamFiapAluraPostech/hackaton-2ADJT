package br.com.fiap.creditcards.infrastructure.repository;

import br.com.fiap.creditcards.infrastructure.schema.CreditCardSchema;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCardSchema, UUID> {

  Optional<CreditCardSchema> findByNumber(String number);
  List<CreditCardSchema> findByCpf(String cpf);
}
