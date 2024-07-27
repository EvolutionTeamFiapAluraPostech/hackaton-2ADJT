package br.com.fiap.authentication.user.infrastructure.repository;

import br.com.fiap.authentication.user.domain.repository.UserRepository;
import br.com.fiap.authentication.user.infrastructure.schema.UserSchema;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSchemaRepository extends JpaRepository<UserSchema, UUID>, UserRepository {

  Optional<UserSchema> findByUsername(String username);
}
