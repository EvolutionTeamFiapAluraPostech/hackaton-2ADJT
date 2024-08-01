package br.com.fiap.payments.infrastructure.schema;

import br.com.fiap.payments.infrastructure.security.UserFromSecurityContext;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.stereotype.Component;

@Component
public class AudittingEntityListener {

  private final UserFromSecurityContext userFromSecurityContext;

  public AudittingEntityListener(UserFromSecurityContext userFromSecurityContext) {
    this.userFromSecurityContext = userFromSecurityContext;
  }

  @PrePersist
  public void onPrePersist(Object entity) {
    if (userFromSecurityContext.getUser() != null) {
      ((BaseSchema) entity).setCreatedBy(userFromSecurityContext.getUser().getSub());
    }
  }

  @PreUpdate
  public void onPreUpdate(Object entity) {
    if (userFromSecurityContext.getUser() != null) {
      ((BaseSchema) entity).setUpdatedBy(userFromSecurityContext.getUser().getSub());
    }
  }
}
