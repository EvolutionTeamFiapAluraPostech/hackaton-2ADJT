package br.com.fiap.authentication.user.domain.entity;

import static br.com.fiap.authentication.user.domain.messages.UserMessages.USER_NAME_MAX_LENGTH;
import static br.com.fiap.authentication.user.domain.messages.UserMessages.USER_NAME_MIN_LENGTH;
import static br.com.fiap.authentication.user.domain.messages.UserMessages.USER_NAME_REQUIRED;

import br.com.fiap.authentication.user.domain.exception.ValidatorException;
import br.com.fiap.authentication.user.domain.valueobject.Password;
import java.util.UUID;
import org.springframework.validation.FieldError;

public class User {

  public static final String USER_ID_FIELD = "ID";
  public static final String USER_NAME_FIELD = "name";
  public static final String USER_ID_REQUIRED = "User ID is required.";
  private UUID id;
  private final String username;
  private final Password password;

  public User(String username, String password) {
    validateUsernameIsNullOrEmpty(username);
    validateUsernameMinLength(username);
    validateUsernameMaxLength(username);
    this.username = username;
    this.password = new Password(password);
  }

  public User(UUID id, String username, String password) {
    this(username, password);
    validateIdIsNull(id);
    this.id = id;
  }

  private void validateIdIsNull(UUID id) {
    if (id == null) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), USER_ID_FIELD,
              USER_ID_REQUIRED));
    }
  }

  private void validateUsernameMaxLength(String name) {
    if (name.trim().length() > 500) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), USER_NAME_FIELD,
              USER_NAME_MAX_LENGTH));
    }
  }

  private void validateUsernameMinLength(String name) {
    if (name.trim().length() < 2) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), USER_NAME_FIELD,
              USER_NAME_MIN_LENGTH));
    }
  }

  private void validateUsernameIsNullOrEmpty(String name) {
    if (name == null || name.isEmpty()) {
      throw new ValidatorException(
          new FieldError(this.getClass().getSimpleName(), USER_NAME_FIELD,
              USER_NAME_REQUIRED));
    }
  }

  public UUID getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public Password getPassword() {
    return password;
  }
}
