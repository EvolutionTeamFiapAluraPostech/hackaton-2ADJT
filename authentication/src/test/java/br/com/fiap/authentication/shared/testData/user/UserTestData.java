package br.com.fiap.authentication.shared.testData.user;

import br.com.fiap.authentication.user.domain.entity.User;
import br.com.fiap.authentication.user.infrastructure.schema.UserSchema;
import java.util.UUID;

public final class UserTestData {

  public static final String DEFAULT_USER_USERNAME = "Morpheus";
  public static final String DEFAULT_USER_PASSWORD = "@Bcd1234";

  public static User createUser() {
    return createUserSchema().getUser();
  }

  public static UserSchema createUserSchema() {
    return UserSchema.builder()
        .id(UUID.randomUUID())
        .username(DEFAULT_USER_USERNAME)
        .password(DEFAULT_USER_PASSWORD)
        .build();
  }
}
