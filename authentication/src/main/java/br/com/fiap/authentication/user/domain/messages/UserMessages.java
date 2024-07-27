package br.com.fiap.authentication.user.domain.messages;

public final class UserMessages {

  private UserMessages(){
  }

  public static final String USER_NAME_REQUIRED = "User name is required.";
  public static final String USER_NAME_MIN_LENGTH = "Min name length is 2 characters.";
  public static final String USER_NAME_MAX_LENGTH = "Max name length is 500 characters.";
  public static final String USER_USERNAME_NOT_FOUND = "User not found by username %s.";
  public static final String USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_NUMBER_CHAR = "Password must have at least one number character.";
  public static final String USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_LOWER_CHAR = "Password must have at least one lower character.";
  public static final String USER_PASSWORD_MUST_HAVE_AT_LEAST_ONE_SPECIAL_CHAR = "Password must have at least one special character @#$%^&+= .";
}
