package br.com.fiap.customers.domain.valueobject;

import br.com.fiap.customers.domain.exception.ValidatorException;
import org.springframework.validation.FieldError;

public class State {

  public static final Integer STATE_FIELD_MAX_LENGTH = 100;
  public static final String STATE_MAX_LENGTH_IS_100_YOU_TYPED_MESSAGE = "State max length is %s. You typed %s";
  private static final String STATE_FIELD = "state";
  private final String stateValue;

  public State(String stateValue) {
    validateStateMaxLength(stateValue);
    this.stateValue = stateValue;
  }

  private void validateStateMaxLength(String stateValue) {
    if (stateValue != null && !stateValue.trim().isEmpty()) {
      var stateLength = stateValue.trim().length();
      if (stateLength > STATE_FIELD_MAX_LENGTH) {
        throw new ValidatorException(new FieldError(this.getClass().getSimpleName(), STATE_FIELD,
            STATE_MAX_LENGTH_IS_100_YOU_TYPED_MESSAGE.formatted(STATE_FIELD_MAX_LENGTH,
                stateLength)));
      }
    }
  }

  public String getStateValue() {
    return stateValue;
  }
}
