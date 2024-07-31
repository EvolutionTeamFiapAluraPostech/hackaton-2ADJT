package br.com.fiap.creditcards.infrastructure.schema;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AudittingEntityListener.class)
public abstract class BaseSchema implements Serializable, AuditableEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  @EqualsAndHashCode.Include
  protected UUID id;

  @Builder.Default
  @Column(nullable = false)
  protected Boolean deleted = false;

  @Builder.Default
  @Version
  @Column(nullable = false)
  protected Long version = 0L;

  @CreationTimestamp
  protected LocalDateTime createdAt;

  protected String createdBy;

  @UpdateTimestamp
  protected LocalDateTime updatedAt;

  protected String updatedBy;
}